package ie.app.easyartistapp.ui.personal.favourite;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.easyartistapp.R;

public class FavouriteViewHolder extends RecyclerView.ViewHolder {
    private CardView topicCardView;
    private ConstraintLayout personalFavouriteItem;
    private ImageView personalFavouriteItemImage;
    private TextView personalFavouriteItemTitle;
    public FavouriteViewHolder(@NonNull View itemView) {
        super(itemView);
        topicCardView = itemView.findViewById(R.id.cardView);
        personalFavouriteItem = itemView.findViewById(R.id.personalFavouriteItem);
        personalFavouriteItemImage = itemView.findViewById(R.id.personalFavouriteItemImage);
        personalFavouriteItemTitle = itemView.findViewById(R.id.personalFavouriteItemTitle);
    }

    public CardView getTopicCardView() {
        return topicCardView;
    }

    public ConstraintLayout getPersonalFavouriteItem() {
        return personalFavouriteItem;
    }

    public ImageView getPersonalFavouriteItemImage() {
        return personalFavouriteItemImage;
    }

    public TextView getPersonalFavouriteItemTitle() {
        return personalFavouriteItemTitle;
    }
}
