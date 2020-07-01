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

import java.io.File;
import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class GalleryRecyclerViewAdapter extends RecyclerView.Adapter<GalleryViewHolder> {
    private static final String TAG = "GalleryRecyclerViewAdap";
    final String galleryFolder="/galleries";

    private Context context;
    private ArrayList<String> imagePaths;

    public GalleryRecyclerViewAdapter(Context context, ArrayList<String> imagePaths){
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
        //load image
        setBitmapImagesFromFile(holder.getGalleryItemImage(),imagePaths.get(position));
    }

    @Override
    public int getItemCount() {
        return imagePaths.size();
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
