package ie.app.easyartistapp.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.util.Log;

import org.tensorflow.lite.DataType;
import org.tensorflow.lite.Interpreter;
import org.tensorflow.lite.gpu.GpuDelegate;
import org.tensorflow.lite.nnapi.NnApiDelegate;
import org.tensorflow.lite.support.common.FileUtil;
import org.tensorflow.lite.support.image.ImageProcessor;
import org.tensorflow.lite.support.image.TensorImage;
import org.tensorflow.lite.support.image.ops.ResizeOp;
import org.tensorflow.lite.support.image.ops.ResizeWithCropOrPadOp;
import org.tensorflow.lite.support.image.ops.Rot90Op;
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer;

import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class StyleTransferModelExecutor {
    private Interpreter interpreterPredict = null;
    private Interpreter interpreterTransform = null;
    private MappedByteBuffer tflitePredictModel = null;
    private MappedByteBuffer tfliteTransformModel = null;
    private final String TAG = "StyleTransferMExec";
    private final int STYLE_IMAGE_SIZE = 256;
    private final int CONTENT_IMAGE_SIZE = 384;
    private final int BOTTLENECK_SIZE = 100;
    private final String STYLE_PREDICT_INT8_MODEL = "style_predict_quantized_256.tflite";
    private final String STYLE_TRANSFER_INT8_MODEL = "style_transfer_quantized_384.tflite";
    private final String STYLE_PREDICT_FLOAT16_MODEL = "style_predict_f16_256.tflite";
    private final String STYLE_TRANSFER_FLOAT16_MODEL = "style_transfer_f16_384.tflite";
    private GpuDelegate gpuDelegate = null;
    private NnApiDelegate nnApiDelegate = null;
    private int numberThreads = 4;
    private Device device = null;
    private final Interpreter.Options tfliteOptions = new Interpreter.Options();

    public enum Device {
        CPU,
        NNAPI,
        GPU
    }

    public enum Model {
        FLOAT,
        QUANTIZED,
    }

    public StyleTransferModelExecutor(Activity activity, Device device) throws IOException {
        this.device = device;
        switch (device) {
            case NNAPI:
                nnApiDelegate = new NnApiDelegate();
                tfliteOptions.addDelegate(nnApiDelegate);
                tflitePredictModel = FileUtil.loadMappedFile(activity, STYLE_PREDICT_INT8_MODEL);
                tfliteTransformModel = FileUtil.loadMappedFile(activity, STYLE_TRANSFER_INT8_MODEL);
                break;
            case GPU:
                gpuDelegate = new GpuDelegate();
                tfliteOptions.addDelegate(gpuDelegate);
                tflitePredictModel = FileUtil.loadMappedFile(activity, STYLE_PREDICT_FLOAT16_MODEL);
                tfliteTransformModel = FileUtil.loadMappedFile(activity, STYLE_TRANSFER_FLOAT16_MODEL);
                break;
            case CPU:
                tflitePredictModel = FileUtil.loadMappedFile(activity, STYLE_PREDICT_INT8_MODEL);
                tfliteTransformModel = FileUtil.loadMappedFile(activity, STYLE_TRANSFER_INT8_MODEL);
                break;
        }


        tfliteOptions.setNumThreads(numberThreads);

        interpreterPredict = new Interpreter(tflitePredictModel, tfliteOptions);
        interpreterTransform = new Interpreter(tfliteTransformModel, tfliteOptions);

    }

    public Bitmap execute(String contentImagePath, String styleImagePath, Context context){
        try{
            Log.i(TAG, "running models");
            Log.d(TAG, styleImagePath.toString());
            Log.d(TAG, contentImagePath.toString());
            Bitmap contentBitmap = BitmapFactory.decodeFile(contentImagePath);
            Bitmap styleBitmap = BitmapFactory.decodeFile(styleImagePath);

            TensorImage contentTensor = convertBitmapToTensorImage(contentBitmap, true);
            TensorImage styleTensor = convertBitmapToTensorImage(styleBitmap, false);

            DataType outputPredictDatatype = interpreterPredict.getOutputTensor(0).dataType();
            int[] styleBottleneckShape = interpreterPredict.getOutputTensor(0).shape();

            TensorBuffer styleBottleneckBuffer  = TensorBuffer.createFixedSize(styleBottleneckShape, outputPredictDatatype);
            interpreterPredict.run(styleTensor.getBuffer(), styleBottleneckBuffer.getBuffer());

            ByteBuffer[] inputsForStyleTransfer = new ByteBuffer[]{contentTensor.getBuffer(), styleBottleneckBuffer.getBuffer()};

//            int[] outputStyleShape = interpreterTransform.getOutputTensor(0).shape();
//            DataType outputStyleDatatype = interpreterPredict.getOutputTensor(0).dataType();

            Float[][][][] imageOutputBuffer = new Float[1][CONTENT_IMAGE_SIZE][CONTENT_IMAGE_SIZE][3];
            HashMap<Integer, Object> outputsForStyleTransfer = new HashMap<Integer, Object>();
            outputsForStyleTransfer.put(0,imageOutputBuffer);
            interpreterTransform.runForMultipleInputsOutputs(inputsForStyleTransfer, outputsForStyleTransfer);
            Bitmap styledImage = convertArrayToBitmap(imageOutputBuffer, CONTENT_IMAGE_SIZE, CONTENT_IMAGE_SIZE);
            return styledImage;
        }catch (Exception ex){
            String exceptionLog = "Something went wrong " + ex.toString();
            Log.d(TAG, exceptionLog);
            Bitmap ret = Bitmap.createBitmap(CONTENT_IMAGE_SIZE, CONTENT_IMAGE_SIZE, Bitmap.Config.RGB_565);
            return ret;
        }

    }

    private TensorImage convertBitmapToTensorImage(Bitmap bitmap, Boolean isPredictInterpreter){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int size = Math.min(height, width);

        ImageProcessor imageProcessor = null;
        if (isPredictInterpreter) {
            imageProcessor =
                    new ImageProcessor.Builder()
                            // Center crop the image to the largest square possible
                            .add(new ResizeWithCropOrPadOp(size, size))
                            // Resize using Bilinear or Nearest neighbour
                            .add(new ResizeOp(CONTENT_IMAGE_SIZE, CONTENT_IMAGE_SIZE, ResizeOp.ResizeMethod.BILINEAR))
                            .build();
        }else{
            imageProcessor =
                    new ImageProcessor.Builder()
                            // Center crop the image to the largest square possible
                            .add(new ResizeWithCropOrPadOp(size, size))
                            // Resize using Bilinear or Nearest neighbour
                            .add(new ResizeOp(STYLE_IMAGE_SIZE, STYLE_IMAGE_SIZE, ResizeOp.ResizeMethod.BILINEAR))
                            .build();
        }

        TensorImage tImage = null;
        if(device == Device.GPU){
            tImage = new TensorImage(DataType.FLOAT32);
        }else{
            tImage = new TensorImage(DataType.FLOAT32);
        }

        tImage.load(bitmap);
        tImage = imageProcessor.process(tImage);

        return tImage;
    }

    private Bitmap convertArrayToBitmap(Float[][][][] imageArray, int imageWidth, int imageHeight){
        Bitmap.Config conf = Bitmap.Config.ARGB_8888;
        Bitmap styledImage = Bitmap.createBitmap(imageWidth, imageHeight, conf);
        for(int x = 0; x < imageArray[0].length; x++){
            for(int y = 0; y < imageArray[0][0].length; y++){
                int color = Color.rgb(
                        (Math.round(imageArray[0][x][y][0] * 255)),
                        (Math.round(imageArray[0][x][y][1] * 255)),
                        (Math.round(imageArray[0][x][y][2] * 255))
                );

                styledImage.setPixel(y, x, color);
            }
        }
        return styledImage;
    }

//    private MappedByteBuffer loadModelFile(Context context , String modelFile) {
//        FileDescriptor fileDescriptor = context.assets.openFd(modelFile);
//        FileInputStream inputStream = new FileInputStream(fileDescriptor.fileDescriptor);
//        Channel fileChannel = inputStream.getChannel();
//        val startOffset = fileDescriptor.
//        val declaredLength = fileDescriptor.declaredLength
//        val retFile = fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength)
//        fileDescriptor.close()
//        return retFile
//    }

}
