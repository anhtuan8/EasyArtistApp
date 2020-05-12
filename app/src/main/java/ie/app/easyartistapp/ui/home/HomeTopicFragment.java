/**
 * Now this Fragment is exactly the same with HomeFragment and isn't used.
 * In the future, viewing article list may be the starting HomeFragment.
 * Thus, topic list view will be moved from HomeFragment to another activity navigated from the drawer.
 */

package ie.app.easyartistapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class HomeTopicFragment extends Fragment {
    private HomeViewModel homeViewModel;

    private ArrayList<String> articleImages = new ArrayList<>();
    private ArrayList<String> articleTitles = new ArrayList<>();
    private ArrayList<String> articleDescriptions = new ArrayList<>();

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home_topic, container, false);
        TextView topicName = root.findViewById(R.id.topicName);
        //set topicName = topicName f"rom HomeViewModel (implement later)
        topicName.setText("Topic Name");

        Log.d(TAG, "onCreateView: started");

        initImageBitmaps(root);
        return root;
    }

    private void initImageBitmaps(View view){
        Log.d(TAG, "initImageBitmaps: started");

        articleImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        articleTitles.add("Starry Night");
        articleDescriptions.add("Buc tranh cua danh hoa Van gogh");

        articleImages.add("https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg");
        articleTitles.add("The Last Supper");
        articleDescriptions.add("Bua an cuoi cung cua Chua Jesus cung cac mon do");

        articleImages.add("https://www.wallpaperflare.com/static/431/740/850/the-divine-comedy-dante-s-inferno-dante-alighieri-gustave-dor%C3%A9-wallpaper.jpg");
        articleTitles.add("Man on canoe");
        articleDescriptions.add("Dia nguc");

        articleImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        articleTitles.add("Starry Night");
        articleDescriptions.add("Buc tranh cua danh hoa Van gogh");

        articleImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        articleTitles.add("Starry Night");
        articleDescriptions.add("Buc tranh cua danh hoa Van gogh");

        articleImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        articleTitles.add("Starry Night");
        articleDescriptions.add("Buc tranh cua danh hoa Van gogh");

        initRecycleView(view);
    }

    private void initRecycleView(View view){
        Log.d(TAG, "initRecycleView: started");
        RecyclerView homeRecyclerView = view.findViewById(R.id.home_article_list);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(),articleImages,articleTitles,articleDescriptions);
        homeRecyclerView.setAdapter(adapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
