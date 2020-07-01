package ie.app.easyartistapp.ui.topic;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Topic;
import ie.app.easyartistapp.ui.ArticleInATopic.ArticlesInATopicActivity;
import ie.app.easyartistapp.ui.ArticleInATopic.ArticlesInATopicViewModel;

public class TopicRecyclerViewAdapter extends RecyclerView.Adapter<TopicRecyclerViewHolder> {
    private Context context;
    private ArrayList<Topic> topics;
    public TopicRecyclerViewAdapter(Context context, ArrayList<Topic> topics){
        this.context = context;
        this.topics = topics;
    }
    @NonNull
    @Override
    public TopicRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.recycler_topic_item,parent,false);
        TopicRecyclerViewHolder holder = new TopicRecyclerViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TopicRecyclerViewHolder holder, int position) {
        Topic topic = topics.get(position);
        holder.getTopicDescription().setText(topic.getTopic_info());
        holder.getTopicDescription().setVisibility(View.GONE);
        holder.getTopicName().setText(topic.getName_topic());
        holder.getContainer().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ArticlesInATopicActivity.class);
                Bundle articleBundle = new Bundle();
                articleBundle.putString("topicId", topic.getTopic_id());
                articleBundle.putString("topicName", topic.getName_topic());
                intent.putExtras(articleBundle);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return topics.size();
    }
}
