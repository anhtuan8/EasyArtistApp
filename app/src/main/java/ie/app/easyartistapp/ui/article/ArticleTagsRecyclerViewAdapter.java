package ie.app.easyartistapp.ui.article;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class ArticleTagsRecyclerViewAdapter extends RecyclerView.Adapter<ArticleTagsRecyclerViewHolder> {
    private static final String TAG = "ArticleTagsRecyclerView";
    private Context context;
    private ArrayList<String> tagNames;

    public ArticleTagsRecyclerViewAdapter(Context context, ArrayList<String> tagNames){
        this.tagNames = tagNames;
        this.context = context;
    }

    @NonNull
    @Override
    public ArticleTagsRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tagitem,parent,false);
        ArticleTagsRecyclerViewHolder holder = new ArticleTagsRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ArticleTagsRecyclerViewHolder holder, final int position) {
        holder.getTagName().setText(tagNames.get(position));
        holder.getTagItem().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked" + tagNames.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return tagNames.size();
    }
}
