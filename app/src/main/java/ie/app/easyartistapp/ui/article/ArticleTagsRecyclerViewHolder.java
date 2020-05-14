package ie.app.easyartistapp.ui.article;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.easyartistapp.R;

public class ArticleTagsRecyclerViewHolder extends RecyclerView.ViewHolder {
    CardView tagCardView;
    ConstraintLayout tagItem;
    TextView tagName;

    public ArticleTagsRecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        tagCardView = itemView.findViewById(R.id.tagView);
        tagItem = itemView.findViewById(R.id.tagItem);
        tagName = itemView.findViewById(R.id.tagName);
    }

    public CardView getTagCardView() {
        return tagCardView;
    }

    public ConstraintLayout getTagItem() {
        return tagItem;
    }

    public TextView getTagName() {
        return tagName;
    }
}
