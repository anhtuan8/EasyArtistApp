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
    private CardView cardView;
    private ConstraintLayout item;
    private ImageView image;
    private TextView title, description;

    public HomeViewHolder(@NonNull View itemView) {
        super(itemView);
        cardView = itemView.findViewById(R.id.cardView);
        item = itemView.findViewById(R.id.item);
        image = itemView.findViewById(R.id.image);
        title = itemView.findViewById(R.id.title);
        description = itemView.findViewById(R.id.description);
    }

    public CardView getCardView() {
        return cardView;
    }

    public ConstraintLayout getItem() {
        return item;
    }

    public ImageView getImage() {
        return image;
    }

    public TextView getDescription() {
        return description;
    }

    public TextView getTitle() {
        return title;
    }

}
