package ie.app.easyartistapp.ui.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.camera.core.CameraSelector;
import androidx.camera.core.CameraX;
import androidx.camera.core.ImageAnalysis;
import androidx.camera.core.ImageCapture;
import androidx.camera.core.ImageCaptureException;
import androidx.camera.core.ImageProxy;
import androidx.camera.core.Preview;
import androidx.camera.view.PreviewView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import androidx.camera.core.Camera;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.concurrent.Executors;
import java.util.Locale;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import androidx.camera.lifecycle.ProcessCameraProvider;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.LifecycleOwner;
import java.text.SimpleDateFormat;
import java.util.concurrent.Executors;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.common.util.concurrent.ListenableFuture;

import ie.app.easyartistapp.MainActivity;
import ie.app.easyartistapp.R;

import static androidx.camera.core.AspectRatio.RATIO_4_3;

public class ContentCameraActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback{

    private static final String TAG = "BINDING_ERROR";
    private int CAMERA_REQUEST_CODE_PERMISSIONS = 0;
    private int GALLERY_REQUEST_CODE_PERMISSIONS = 0;
    private final String[] CAMERA_REQUIRED_PERMISSIONS = new String[]{"android.permission.CAMERA"};
    private final String[] GALLERY_REQUIRED_PERMISSIONS = new String[]{"android.permission.READ_EXTERNAL_STORAGE"};
    private ListenableFuture<ProcessCameraProvider> cameraProviderFuture;
    private PreviewView previewView = null;
    private FloatingActionButton switch_camera_fab = null;
    private int lensFacing = CameraSelector.LENS_FACING_BACK;
    private FloatingActionButton capture_image_fab = null;
    private FloatingActionButton gallery_fab = null;
    private ProcessCameraProvider cameraProvider = null;
    private ImageView imageView = null;
    private ImageCapture imageCapture = null;
    private Camera camera = null;
    private final String ACTION_GALLERY = "ie.app.easyartistapp.ui.camera.ACTION_GALLERY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_camera);

        previewView = findViewById(R.id.camera_preview);
        switch_camera_fab = findViewById(R.id.switch_camera_fab);
        capture_image_fab = findViewById(R.id.capture_image_fab);
        gallery_fab = findViewById(R.id.gallery_fab);
        imageView = findViewById(R.id.imageView);
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


        if(cameraPermissionsGranted()){
            setUpCamera(); //start camera if permission has been granted by user
        } else{
            ActivityCompat.requestPermissions(this, CAMERA_REQUIRED_PERMISSIONS, CAMERA_REQUEST_CODE_PERMISSIONS);
        }


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
                File file = new File(Environment.getExternalStorageDirectory() + "/" + System.currentTimeMillis() + ".png");
                ImageCapture.OutputFileOptions outputFileOptions = new ImageCapture.OutputFileOptions.Builder(file).build();
                imageCapture.takePicture(outputFileOptions, Executors.newCachedThreadPool(),  new ImageCapture.OnImageSavedCallback(){
                    @Override
                    public void onImageSaved(@NonNull ImageCapture.OutputFileResults outputFileResults) {
                       // Toast.makeText(getApplicationContext(), "Saving image", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), StyleImageActivity.class);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, file.getAbsolutePath());
                        startActivity(intent);
                    }

                    @Override
                    public void onError(@NonNull ImageCaptureException exception) {
                        Toast.makeText(ContentCameraActivity.this, "Error in saving image", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        gallery_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (galleryPermissionsGranted()){
                    accessGallery();

                }else{
                    ActivityCompat.requestPermissions(ContentCameraActivity.this, GALLERY_REQUIRED_PERMISSIONS, GALLERY_REQUEST_CODE_PERMISSIONS);
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


        if (requestCode == GALLERY_REQUEST_CODE_PERMISSIONS){
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessGallery();
            } else {
                // Permission request was denied.
                Toast.makeText(this, "Gallery Access Denied", Toast.LENGTH_SHORT).show();
                gallery_fab.setColorFilter(Color.GRAY);

            }
        }

        // BEGIN_INCLUDE(onRequestPermissionsResult)
        else if (requestCode == CAMERA_REQUEST_CODE_PERMISSIONS) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                setUpCamera();
            } else {
                // Permission request was denied.
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            }
        }


        // END_INCLUDE(onRequestPermissionsResult)
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE_PERMISSIONS) {
            if (resultCode == RESULT_OK && data != null) {
                Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                if (selectedImage != null) {
                    Cursor cursor = getContentResolver().query(selectedImage,
                            filePathColumn, null, null, null);
                    if (cursor != null) {
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        String picturePath = cursor.getString(columnIndex);
                        cursor.close();
                        Intent intent = new Intent(this, StyleImageActivity.class);
                        intent.putExtra("UUID", ACTION_GALLERY);
                        intent.putExtra(MediaStore.EXTRA_OUTPUT, picturePath);
                        startActivity(intent);
                        //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                    }
                }
            }
        }else{
            Toast.makeText(this, "No success!", Toast.LENGTH_SHORT).show();
        }
    }

    void accessGallery(){
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, GALLERY_REQUEST_CODE_PERMISSIONS);
    }

    void setUpCamera(){
        cameraProviderFuture = ProcessCameraProvider.getInstance(this);
        cameraProviderFuture.addListener(() -> {
            try {
                cameraProvider = cameraProviderFuture.get();
                bindCameraUseCases();
            } catch(ExecutionException | InterruptedException e){
                Log.v("CameraProvider", cameraProvider.toString());
            }
        }, ContextCompat.getMainExecutor(this));


    }

    void bindCameraUseCases() {

        //int rotation = previewView.getDisplay().getRotation();
        previewView.setPreferredImplementationMode(PreviewView.ImplementationMode.SURFACE_VIEW);
        Preview preview = new Preview.Builder().setTargetAspectRatio(RATIO_4_3).build();
        imageCapture = new ImageCapture.Builder().setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
                                .setTargetRotation(getWindowManager().getDefaultDisplay().getRotation())
                                .setTargetAspectRatio(RATIO_4_3).build();
        CameraSelector cameraSelector = new CameraSelector.Builder()
                .requireLensFacing(lensFacing)
                .build();

        cameraProvider.unbindAll();
        try{
            camera = cameraProvider.bindToLifecycle((LifecycleOwner)this, cameraSelector, preview, imageCapture);
            preview.setSurfaceProvider(previewView.createSurfaceProvider(camera.getCameraInfo()));
        }catch(IllegalStateException ill){
            Log.e(TAG, "Use case binding failed ", ill);
        }

    }

   private Boolean cameraPermissionsGranted(){
        for(String permission: CAMERA_REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
   }

    private Boolean galleryPermissionsGranted(){
        for(String permission: GALLERY_REQUIRED_PERMISSIONS){
            if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                return false;
            }
        }
        return true;
    }

}
