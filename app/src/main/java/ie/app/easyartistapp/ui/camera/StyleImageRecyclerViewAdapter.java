package ie.app.easyartistapp.ui.camera;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.ImagesItem;

public class StyleImageRecyclerViewAdapter extends RecyclerView.Adapter<StyleImageRecyclerViewAdapter.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mNames = new ArrayList<>();
    private ArrayList<String> mImageUrls = new ArrayList<>();
    private Context mContext;
    private OnStyleImageListener mOnStyleImageListener;
    private int lastItemChosen = -1;
    private List<ImagesItem>imageItems = null;

    public StyleImageRecyclerViewAdapter(Context context, List<ImagesItem> imagesItemlist, OnStyleImageListener onStyleImageListener){
        imageItems = imagesItemlist;
        mContext = context;
        this.mOnStyleImageListener = onStyleImageListener;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        Uri filepath = Uri.parse("file:///android_asset/thumbnails/" + imageItems.get(position).getImage_url());
        Glide.with(mContext)
                .asBitmap()
                .load(filepath)
                .into(holder.image);


        //if(poststyleChooseIndex )
        holder.image.setBorderOverlay(false);
        holder.image.setBorderWidth(0);

      //  holder.name.setText(mNames.get(position));

//        holder.image.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                //Log.d(TAG, "onClick: clicked on an image: " + mNames.get(position));
//                //holder.image.setBorderColor();
//               // holder.image.setBorderWidth(2);
//                holder.image.setBorderWidth(5);
//                holder.image.setBorderOverlay(true);
//               // Toast.makeText(mContext, mNames.get(position), Toast.LENGTH_SHORT).show();
//
//
//            }
//        });
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_styleimageslist, parent, false);
        return new ViewHolder(view, mOnStyleImageListener);
    }

    @Override
    public int getItemCount() {
        return imageItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        CircleImageView image;
        OnStyleImageListener onStyleImageListener;
       // TextView name;

        public ViewHolder(View itemView, OnStyleImageListener onStyleImageListener){
            super(itemView);
            image = itemView.findViewById(R.id.style_image_view);
            itemView.setOnClickListener(this);
            this.onStyleImageListener = onStyleImageListener;
          //  name = itemView.findViewById(R.id.name);
        }

        @Override
        public void onClick(View v) {

            image.setBorderOverlay(true);
            image.setBorderWidth(10);

//            lastItemChosen = getAdapterPosition();
//            if(lastItemChosen != -1 || lastItemChosen != getAdapterPosition() ){
//                notifyItemChanged(lastItemChosen, null);
//                notifyItemChanged(getAdapterPosition(), null);
//            }

            for (int i=0; i <= imageItems.size(); i++){
                if (i != getAdapterPosition()){
                    notifyItemChanged(i, null);
                }

            }
            //image.setBorderColor();
            onStyleImageListener.onStyleImageClick(getAdapterPosition());
        }
    }

    public interface OnStyleImageListener{
        void onStyleImageClick(int position);
    }
}
