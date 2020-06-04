package ie.app.easyartistapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.ui.article.ArticleActivity;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeViewHolder> {
    private static final String TAG = "HomeRecyclerViewAdapter";

    private ArrayList<String> images = new ArrayList<>();
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> descriptions = new ArrayList<>();
    Context context;

    public HomeRecyclerViewAdapter(Context context, ArrayList<String> images, ArrayList<String> titles, ArrayList<String> descriptions){
        this.context = context;
        this.images = images;
        this.titles = titles;
        this.descriptions = descriptions;
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listitem,parent,false);
        HomeViewHolder holder = new HomeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final HomeViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder called");
        //load image to position
        Glide.with(context).asBitmap().load(images.get(position)).into(holder.getTopicImage());
        //bind topic title and description
        holder.getTopicTitle().setText(titles.get(position));
        holder.getTopicDescription().setText(descriptions.get(position));
        holder.getTopicItem().setOnClickListener(new View.OnClickListener() {
            @Override
            /**
             * Call article list in corresponding topic title, or article activity with corresponding article title
             */
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked " + titles.get(position));
                Toast.makeText(context, titles.get(position),Toast.LENGTH_SHORT).show();
                if(holder.isTopicList()){
                    //call HomeTopicFragment
                    Log.d(TAG, "calling Home Topic Fragment.");
                }
                else{
                    //call HomeArticleFragment
                    Log.d(TAG, "calling HomeArticleActivity");
                    Activity activityFromContext = (Activity) context;
                    Intent intent = new Intent(activityFromContext, ArticleActivity.class);
                    activityFromContext.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

}
