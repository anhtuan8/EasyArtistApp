package ie.app.easyartistapp.ui.personal.favourite;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Article;
import ie.app.easyartistapp.ui.article.ArticleActivity;
import ie.app.easyartistapp.ui.home.HomeViewHolder;

public class FavouriteRecyclerViewAdapter extends RecyclerView.Adapter<FavouriteViewHolder> {
    private static final String TAG = "FavouriteRecyclerViewAd";

    private ArrayList<Article> favoriteArticles = new ArrayList<>();

    private Context context;

    public FavouriteRecyclerViewAdapter(Context context, ArrayList<Article> articleArrayList){
        this.context = context;
        this.favoriteArticles = articleArrayList;
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
        final Article article = favoriteArticles.get(position);
        Log.d(TAG, "onBindViewHolder: " + article.getName());
        holder.getPersonalFavouriteItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked on " + article.getName());
                Toast.makeText(context, favoriteArticles.get(position).getName(),Toast.LENGTH_SHORT).show();
                Activity activityFromContext = (Activity) context;
                Intent intent = new Intent(activityFromContext, ArticleActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("articleId",article.getArticle_id());
                intent.putExtras(bundle);
                activityFromContext.startActivity(intent);
            }
        });
        Glide.with(context).asBitmap().load(article.getImage_link()).into(holder.getPersonalFavouriteItemImage());
        holder.getPersonalFavouriteItemTitle().setText(article.getName());
    }

    @Override
    public int getItemCount() {
        return favoriteArticles.size();
    }
}
