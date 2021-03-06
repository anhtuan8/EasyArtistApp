package ie.app.easyartistapp.ui.personal.gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private static final String TAG = "GalleryRecyclerViewAdap";
    final String galleryFolder="/galleries";

    private Context context;
    private File[] imagePaths;

    public GalleryRecyclerViewAdapter(Context context, File[] imagePaths){
        this.context = context;
        this.imagePaths = imagePaths;
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_gallery_item,parent,false);
        GalleryViewHolder holder = new GalleryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, final int position) {
        holder.getGalleryItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on pictures number " + position);
            }
        });
        //setBitmapImagesFromFile(holder.getGalleryItemImage(),imagePaths.get(position));
        File imagePath = imagePaths[position];
        Glide.with(context)
                .load(imagePath) // Uri of the picture
                .override(200, 200)
                .into(holder.getGalleryItemImage());
    }

    @Override
    public int getItemCount() {
        return imagePaths.length;
    }

    /**
     *
     * @param imageView
     * @param path
     */
    private void setBitmapImagesFromFile(ImageView imageView, String path){
        File imgFile = new  File(path);

        if(imgFile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);
        }
    }
}
