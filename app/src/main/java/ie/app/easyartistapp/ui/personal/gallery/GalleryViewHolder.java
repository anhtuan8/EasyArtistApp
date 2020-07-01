package ie.app.easyartistapp.ui.personal.gallery;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.easyartistapp.R;

public class GalleryViewHolder extends RecyclerView.ViewHolder {
    private CardView galleryCardView;
    private ConstraintLayout galleryItem;
    private ImageView galleryItemImage;
    public GalleryViewHolder(@NonNull View itemView) {
        super(itemView);
        galleryCardView = itemView.findViewById(R.id.galleryCardView);
        galleryItem = itemView.findViewById(R.id.galleryItem);
        galleryItemImage = itemView.findViewById(R.id.galleryItemImage);
    }

    public CardView getGalleryCardView() {
        return galleryCardView;
    }

    public ConstraintLayout getGalleryItem() {
        return galleryItem;
    }

    public ImageView getGalleryItemImage() {
        return galleryItemImage;
    }
}

