package ie.app.easyartistapp.ui.topic;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import ie.app.easyartistapp.R;

public class TopicRecyclerViewHolder extends RecyclerView.ViewHolder {
    private TextView topicName;
    private TextView topicDescription;
    private CardView container;

    public TopicRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        topicDescription = itemView.findViewById(R.id.description);
        topicName = itemView.findViewById(R.id.title);
        container = itemView.findViewById(R.id.cardView);
    }

    public TextView getTopicDescription() {
        return topicDescription;
    }

    public CardView getContainer() {
        return container;
    }

    public TextView getTopicName() {
        return topicName;
    }
}
