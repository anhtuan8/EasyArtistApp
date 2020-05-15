package ie.app.easyartistapp.ui.personal.favourite;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.ui.home.HomeViewHolder;

public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private static final String TAG = "FavouriteRecyclerViewAd";

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();

    private Context context;

    public FavouriteRecyclerViewAdapter(Context context, FavouriteViewModel favouriteViewModel){
        this.context = context;
        this.images = favouriteViewModel.getImages();
        this.titles = favouriteViewModel.getTitles();
    }

    @NonNull
    @Override
    public FavouriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_personal_favourite_item,parent,false);
        FavouriteViewHolder holder = new FavouriteViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteViewHolder holder, final int position) {
        holder.getPersonalFavouriteItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on " + titles.get(position));
            }
        });
        Glide.with(context).asBitmap().load(images.get(position)).into(holder.getPersonalFavouriteItemImage());
        holder.getPersonalFavouriteItemTitle().setText(titles.get(position));
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }
}
