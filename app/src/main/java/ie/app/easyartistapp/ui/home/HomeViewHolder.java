package ie.app.easyartistapp.ui.home;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.easyartistapp.R;

public class HomeViewHolder extends RecyclerView.ViewHolder {
    private boolean isTopicList;
    private CardView topicCardView;
    private ConstraintLayout topicItem;
    private ImageView topicImage;
    private TextView topicTitle, topicDescription;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        if(itemView.findViewById(R.id.home_topic_list) != null){
            isTopicList = true;
        }
        else isTopicList = false;
        topicCardView = itemView.findViewById(R.id.topicCardView);
        topicItem = itemView.findViewById(R.id.topicItem);
        topicImage = itemView.findViewById(R.id.topicImage);
        topicTitle = itemView.findViewById(R.id.topicTitle);
        topicDescription = itemView.findViewById(R.id.topicDescription);
    }

    public CardView getTopicCardView() {
        return topicCardView;
    }

    public ConstraintLayout getTopicItem() {
        return topicItem;
    }

    public ImageView getTopicImage() {
        return topicImage;
    }

    public TextView getTopicDescription() {
        return topicDescription;
    }

    public TextView getTopicTitle() {
        return topicTitle;
    }

    public boolean isTopicList() {
        return isTopicList;
    }
}
