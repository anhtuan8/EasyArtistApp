package ie.app.easyartistapp.ui.camera;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import androidx.camera.core.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import java.io.File;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import java.text.SimpleDateFormat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.common.util.concurrent.ListenableFuture;

import ie.app.easyartistapp.R;

import static androidx.camera.core.AspectRatio.RATIO_4_3;

public class ContentCameraActivity extends AppCompatActivity {

    private static final String TAG = "BINDING_ERROR";
    private PreviewView preview = null;
    private ImageCapture imageCapture = null;
    private ImageAnalysis imageAnalyzer = null;
    private Camera camera = null;
    private File outputDirectory = null;
    private ExecutorService cameraExecutor = null;
    private int REQUEST_CODE_PERMISSIONS = 10;
    private final String[] REQUIRED_PERMISSIONS = new String[]{Manifest.permission.CAMERA};
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView = null;
    private FloatingActionButton switch_camera_fab = null;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private ProcessCameraProvider cameraProvider = null;
    private FloatingActionButton capture_image_fab = null;
    private String FILENAME = "yyyy-MM-dd-HH-mm-ss-SSS";
    private String PHOTO_EXTENSION = ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_camera);
        previewView = findViewById(R.id.camera_preview);
        switch_camera_fab = findViewById(R.id.switch_camera_fab);
        capture_image_fab = findViewById(R.id.capture_image_fab);

        //set up back button to return HOME
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        switch_camera_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (lensFacing == CameraSelector.LENS_FACING_BACK){
                    lensFacing = CameraSelector.LENS_FACING_FRONT;
                }else{
                    lensFacing = CameraSelector.LENS_FACING_BACK;
                }

                bindCameraUseCases();
            }
        });

        capture_image_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        if(allPermissionsGranted()){
            setUpCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS);
        }

    }

    void setUpCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                ProcessCameraProvider cameraProvider = cameraProviderFuture.get();
            } catch(ExecutionException | InterruptedException e){

            }
        }, ContextCompat.getMainExecutor(this));

        bindCameraUseCases();
    }

    void bindCameraUseCases() {

        int rotation = previewView.getDisplay().getRotation();
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);
        Preview preview = new Preview.Builder().setTargetRotation(rotation).setTargetAspectRatio(RATIO_4_3).build();
        ImageCapture imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                .setTargetAspectRatio(RATIO_4_3).setTargetRotation(rotation).build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();

        cameraProvider.unbindAll();
        try{
            Camera camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
        }catch(IllegalStateException ill){
            Log.e(TAG, "Use case binding failed ", ill);
        }

        preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));
    }

   private Boolean allPermissionsGranted(){
        return false;
   }

   private File createFile(File baseFolder, String format, String extension){

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
        String strDate = simpleDateFormat.format(System.currentTimeMillis()) + extension);
        File file = new File(baseFolder, strDate);
        return file;
   }


}
