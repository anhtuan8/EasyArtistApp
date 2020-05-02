package ie.app.easyartistapp.ui.home;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class HomeRecyclerViewAdapter extends RecyclerView.Adapter<HomeRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "HomeRecyclerViewAdapter";

    private ArrayList<String> topicImages = new ArrayList<>();
    private ArrayList<String> topicTitles = new ArrayList<>();
    private ArrayList<String> topicDescriptions = new ArrayList<>();
    Context topicContext;

    public HomeRecyclerViewAdapter(Context context, ArrayList<String> images, ArrayList<String> titles, ArrayList<String> descriptions){
        this.topicContext = context;
        this.topicImages = images;
        this.topicTitles = titles;
        this.topicDescriptions = descriptions;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_listitem,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        Log.d(TAG,"onBindViewHolder called");
        //load image to position
        Glide.with(topicContext).asBitmap().load(topicImages.get(position)).into(holder.topicImage);
        //bind topic title and description
        holder.topicTitle.setText(topicTitles.get(position));
        holder.topicDescription.setText(topicDescriptions.get(position));
        holder.topicItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: Clicked " + topicTitles.get(position));

                Toast.makeText(topicContext,topicTitles.get(position),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return topicTitles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CardView topicCardView;
        ConstraintLayout topicItem;
        ImageView topicImage;
        TextView topicTitle, topicDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            topicCardView = itemView.findViewById(R.id.topicCardView);
            topicItem = itemView.findViewById(R.id.topicItem);
            topicImage = itemView.findViewById(R.id.topicImage);
            topicTitle = itemView.findViewById(R.id.topicTitle);
            topicDescription = itemView.findViewById(R.id.topicDescription);
        }
    }
}
