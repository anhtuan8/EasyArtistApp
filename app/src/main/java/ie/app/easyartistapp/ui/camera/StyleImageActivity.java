package ie.app.easyartistapp.ui.camera;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import ie.app.easyartistapp.R;

public class StyleImageActivity extends AppCompatActivity implements StyleImageRecyclerViewAdapter.OnStyleImageListener, LoaderManager.LoaderCallbacks<Bitmap> {



    private ImageView imageView = null;
    private TextView content_caption_view = null;
    private final String ACTION_GALLERY = "ie.app.easyartistapp.ui.camera.ACTION_GALLERY";
    private static final String TAG = "MainActivity";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private String contentImagePath = null;
    private String styleImagePath = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_image);
        imageView = findViewById(R.id.imageView);
        content_caption_view = findViewById(R.id.content_text);
        Intent intent = getIntent();
        if (intent != null) {
            String uuid = intent.getExtras().getString("UUID");
            if (uuid.equals(ACTION_GALLERY)) {
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                contentImagePath = picturePath;
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            } else {
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                Log.d("Picture", picturePath);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }
        getImages();
    }

    private void getImages() {
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");


        mImageUrls.add("https://i.redd.it/tpsnoz5bzo501.jpg");
        mNames.add("Trondheim");

        mImageUrls.add("https://i.redd.it/qn7f9oqu7o501.jpg");
        mNames.add("Portugal");

        mImageUrls.add("https://i.redd.it/j6myfqglup501.jpg");
        mNames.add("Rocky Mountain National Park");


        mImageUrls.add("https://i.redd.it/0h2gm1ix6p501.jpg");
        mNames.add("Mahahual");

        mImageUrls.add("https://i.redd.it/k98uzl68eh501.jpg");
        mNames.add("Frozen Lake");


        mImageUrls.add("https://i.redd.it/glin0nwndo501.jpg");
        mNames.add("White Sands Desert");

        mImageUrls.add("https://i.redd.it/obx4zydshg601.jpg");
        mNames.add("Austrailia");

        mImageUrls.add("https://i.imgur.com/ZcLLrkY.jpg");
        mNames.add("Washington");

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
        if (getSupportLoaderManager().getLoader(0) != null) {
            getSupportLoaderManager().initLoader(0, null, this);
        }
    }

    @NonNull
    @Override
    public Loader<Bitmap> onCreateLoader(int id, @Nullable Bundle args) {
        return new MLExecutionLoader(StyleImageActivity.this, contentImagePath, styleImagePath, StyleImageActivity.this);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Bitmap> loader, Bitmap data) {
        imageView.setImageBitmap(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Bitmap> loader) {

    }




}
