package ie.app.easyartistapp.ui.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.exifinterface.media.ExifInterface;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.share.Sharer;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.ImagesItem;
import ie.app.easyartistapp.entityObject.StyleImageObjects;

public class StyleImageActivity extends AppCompatActivity implements StyleImageRecyclerViewAdapter.OnStyleImageListener, LoaderManager.LoaderCallbacks<Bitmap> {



    private static final String LOG = "CONTENT_PATH";
    private ImageView imageView = null;
    private TextView content_caption_view = null;
    private final String ACTION_GALLERY = "ie.app.easyartistapp.ui.camera.ACTION_GALLERY";
    private static final String TAG = "MainActivity";
//    private ArrayList<String> mNames = new ArrayList<>();
//    private ArrayList<String> mImageUrls = null;
    private String contentImagePath = null;
    private String styleImagePath = null;
    private ProgressBar progressBar = null;
    private StyleImageObjects imageObject = null;
    private List<ImagesItem>  imageItems = null;
//    private FrameLayout constraintLayout = null;
//    private ShareDialog shareDialog = null;
//    private CallbackManager callbackManager = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_image);
        imageView = findViewById(R.id.imageView);
        content_caption_view = findViewById(R.id.content_text);
        progressBar = findViewById(R.id.progress_bar);
        Gson gson = new Gson();
        String imageJson = loadJSONFromAsset();
        imageObject = gson.fromJson(imageJson, StyleImageObjects.class);
        imageItems = imageObject.getImages();
        androidx.appcompat.widget.Toolbar toolbar = (Toolbar) findViewById(R.id.style_toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

//        callbackManager = CallbackManager.Factory.create();
//        shareDialog = new ShareDialog(this);
//        shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
//            @Override
//            public void onSuccess(Sharer.Result result) {
//                Log.d("Facebook", "Sucesss");
//            }
//
//            @Override
//            public void onCancel() {
//                Log.d("Facebook", "Cancel");
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//                Log.d("Facebook", "Error");
//            }
//        });
       // shareButton = (ShareButton)findViewById(R.id.fb_share_button);


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        Intent intent = getIntent();
        if (intent != null) {
            String uuid = intent.getExtras().getString("UUID");
            if (uuid.equals(ACTION_GALLERY)) {
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                contentImagePath = picturePath;
                Bitmap bitmap = decodeBitmap(picturePath);
                imageView.setImageBitmap(bitmap);
            } else {
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                contentImagePath = picturePath;
                Bitmap bitmap = ImageUtils.decodeBitmap(new File(contentImagePath));
                imageView.setImageBitmap(bitmap);
                //Glide.with(this).load(new File(picturePath)).into(imageView);
            }

        }
        getImages();
        Bundle queryBundle = new Bundle();
        queryBundle.putString("contentPath", contentImagePath);
        queryBundle.putString("stylePath", styleImagePath);
        if(getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, queryBundle, this);
        }

    }

     Bitmap decodeBitmap(String file) {
        // First, decode EXIF data and retrieve transformation matrix
         ExifInterface exif = null;
         try {
             exif = new ExifInterface(file);
         } catch (IOException e) {
             e.printStackTrace();
         }
         Matrix transformation =
                ImageUtils.decodeExifOrientation(
                        exif.getAttributeInt(
                                ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_ROTATE_90
                        )
                );

        // Read bitmap using factory methods, and transform it using EXIF data
        Bitmap bitmap = BitmapFactory.decodeFile(file);
        return Bitmap.createBitmap(
                BitmapFactory.decodeFile(file),
                0, 0, bitmap.getWidth(), bitmap.getHeight(), transformation, true
        );
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.style_menu, menu);

        return true;
    }

    private void getImages() {
//        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");
//
//
//        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
//        mNames.add("Trondheim");
//
//        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
//        mNames.add("Portugal");
//
//        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
//        mNames.add("Rocky Mountain National Park");
//
//
//        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
//        mNames.add("Mahahual");
//
//        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
//        mNames.add("Frozen Lake");
//
//
//        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
//        mNames.add("White Sands Desert");
//
//        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
//        mNames.add("Austrailia");
//
//        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
//        mNames.add("Washington");
        try{
            ArrayList<String> mImageUrls = new ArrayList<String>(Arrays.asList(getApplicationContext().getAssets().list("thumbnails")));
            for
            Log.d("STYLE_IMAGE", mImageUrls.get(0));
        }catch (IOException ioError){
            Log.d("Activity: StyleImageActivity:", ioError.toString());
        }

        initRecyclerView();
    }


    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview");

        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        RecyclerView recyclerView = findViewById(R.id.style_image_recycler);
        recyclerView.setLayoutManager(layoutManager);
        StyleImageRecyclerViewAdapter adapter = new StyleImageRecyclerViewAdapter(this, mNames, mImageUrls, this::onStyleImageClick);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStyleImageClick(int position) {
        styleImagePath = mImageUrls.get(position);
        Log.d(TAG, "onStyleClick: clicked.");
        Bundle queryBundle = new Bundle();
        progressBar.setVisibility(View.VISIBLE);
        imageView.setAlpha((float) 0.4);
        queryBundle.putString("content_url", contentImagePath);
        queryBundle.putString("style_url", styleImagePath);
        
        if(getSupportLoaderManager().getLoader(0) == null){

            getSupportLoaderManager().initLoader(0, queryBundle, this);
        }else{
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
        }

    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        return new MLExecutionLoader(StyleImageActivity.this, contentImagePath, styleImagePath);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        Bitmap bitmap = Bitmap.createScaledBitmap(data, 1440, 1440, true);
//        outAnimation = new AlphaAnimation(1f, 0f);
//        outAnimation.setDuration(200);
//        constraintLayout.setVisibility(View.GONE);
        imageView.setAlpha((float) 1);
        progressBar.setVisibility(View.GONE);
        imageView.setImageBitmap(bitmap);
        getSupportLoaderManager().destroyLoader(0);

    }



    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_save) {

            File mImageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "EasyArtist_StyleImage");
            boolean isDirectoryCreated = mImageDir.exists() || mImageDir.mkdirs();
            //Log.d("File", file.toString());
            if (isDirectoryCreated) {
                File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picture", System.currentTimeMillis() + ".jpg");
                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap bitmap = draw.getBitmap();
                try {
                    FileOutputStream outStream = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream);
                    outStream.flush();
                    outStream.close();
                } catch (IOException IOerr) {
                    Toast.makeText(StyleImageActivity.this, "Can not save file!", Toast.LENGTH_SHORT).show();

                }
                Toast.makeText(StyleImageActivity.this, "Save Image Successfully!", Toast.LENGTH_SHORT).show();

            }

            return true;
        }
        if (id == R.id.action_share) {
            File mImageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "EasyArtist_StyleImage");
            boolean isDirectoryCreated = mImageDir.exists() || mImageDir.mkdirs();
            //Log.d("File", file.toString());
            if (isDirectoryCreated) {
                //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/picture", System.currentTimeMillis() + ".jpg");

                //shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                BitmapDrawable draw = (BitmapDrawable) imageView.getDrawable();
                Bitmap image = draw.getBitmap();
                Log.d("Sharing", "Load image done");
                SharePhoto photo = new SharePhoto.Builder()
                        .setBitmap(image)
                        .build();
                SharePhotoContent content = new SharePhotoContent.Builder()
                        .addPhoto(photo)
                        .build();
                ShareDialog.show(this, content);


//
//                });

            }
        }
        return super.onOptionsItemSelected(item);
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getApplicationContext().getAssets().open("infor_style_image.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }


}








