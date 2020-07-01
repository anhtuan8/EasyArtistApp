package ie.app.easyartistapp.ui.camera;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;

import androidx.exifinterface.media.ExifInterface;

import java.io.File;
import java.io.IOException;

public class ImageUtils {
    public static Matrix decodeExifOrientation(int orientation){
        Matrix matrix = new Matrix();
        // Apply transformation corresponding to declared EXIF orientation
        switch(orientation) {
            case ExifInterface.ORIENTATION_NORMAL:
            case ExifInterface.ORIENTATION_UNDEFINED:
                break;
            case ExifInterface.ORIENTATION_ROTATE_90:
                matrix.postRotate(90F);
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                matrix.postRotate(180F);
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                matrix.postRotate(270F);
                break;
            case ExifInterface.ORIENTATION_FLIP_HORIZONTAL:
                matrix.postScale(-1F, 1F);
                break;
            case ExifInterface.ORIENTATION_FLIP_VERTICAL:
                matrix.postScale(1F, -1F);
                break;
            case ExifInterface.ORIENTATION_TRANSPOSE:
                matrix.postScale(-1F, 1F);
                matrix.postRotate(270F);
                break;
            case ExifInterface.ORIENTATION_TRANSVERSE:
                matrix.postScale(-1F, 1F);
                matrix.postRotate(90F);
                break;
            default:
                throw  new IllegalArgumentException("Invalid orientation: $orientation");
        }
        // Return the resulting matrix
        return matrix;
    }

    public static void setExifOrientation( String filePath, String value) throws IOException {
        ExifInterface exif = new ExifInterface(filePath);
        exif.setAttribute(
                ExifInterface.TAG_ORIENTATION, value
        );
        exif.saveAttributes();
    }

    public static int computeExifOrientation(int rotationDegrees, Boolean mirrored) {
        if (rotationDegrees == 0 && !mirrored) {
            return ExifInterface.ORIENTATION_NORMAL;
        } else if (rotationDegrees == 0 && mirrored) {
            return ExifInterface.ORIENTATION_FLIP_HORIZONTAL;
        } else if (rotationDegrees == 180 && !mirrored) {
            return ExifInterface.ORIENTATION_ROTATE_180;
        } else if (rotationDegrees == 180 && mirrored) {
            return ExifInterface.ORIENTATION_FLIP_VERTICAL;
        } else if (rotationDegrees == 270 && mirrored) {
            return ExifInterface.ORIENTATION_TRANSVERSE;
        } else if (rotationDegrees == 90 && !mirrored) {
            return ExifInterface.ORIENTATION_ROTATE_90;
        } else if (rotationDegrees == 270 && mirrored) {
            return ExifInterface.ORIENTATION_ROTATE_270;
        } else if (rotationDegrees == 270 && !mirrored) {
            return ExifInterface.ORIENTATION_TRANSVERSE;
        } else {
            return ExifInterface.ORIENTATION_UNDEFINED;
        }
    }

    public static Bitmap decodeBitmap(File file){
        // First, decode EXIF data and retrieve transformation matrix
        ExifInterface exif = null;
        try {
            exif = new ExifInterface(file.getAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matrix transformation =
                decodeExifOrientation(
                        exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION,  ExifInterface.ORIENTATION_ROTATE_90
                        )
                );

        // Read bitmap using factory methods, and transform it using EXIF data
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        return Bitmap.createBitmap(
                BitmapFactory.decodeFile(file.getAbsolutePath()),
                0, 0, bitmap.getWidth(), bitmap.getHeight(), transformation, true
        );
    }
}
