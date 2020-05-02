package ie.app.easyartistapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ArrayList<String> topicImages = new ArrayList<>();
    private ArrayList<String> topicTitles = new ArrayList<>();
    private ArrayList<String> topicDescriptions = new ArrayList<>();

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView: started");

        initImageBitmaps(root);
        return root;
    }

    private void initImageBitmaps(View view){
        Log.d(TAG, "initImageBitmaps: started");

        topicImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        topicTitles.add("Starry Night");
        topicDescriptions.add("Buc tranh cua danh hoa Van gogh");

        topicImages.add("https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg");
        topicTitles.add("The Last Supper");
        topicDescriptions.add("Bua an cuoi cung cua Chua Jesus cung cac mon do");

        topicImages.add("https://www.wallpaperflare.com/static/431/740/850/the-divine-comedy-dante-s-inferno-dante-alighieri-gustave-dor%C3%A9-wallpaper.jpg");
        topicTitles.add("Man on canoe");
        topicDescriptions.add("Dia nguc");

        topicImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        topicTitles.add("Starry Night");
        topicDescriptions.add("Buc tranh cua danh hoa Van gogh");

        topicImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        topicTitles.add("Starry Night");
        topicDescriptions.add("Buc tranh cua danh hoa Van gogh");

        topicImages.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        topicTitles.add("Starry Night");
        topicDescriptions.add("Buc tranh cua danh hoa Van gogh");

        initRecycleView(view);
    }

    private void initRecycleView(View view){
        Log.d(TAG, "initRecycleView: started");
        RecyclerView homeRecyclerView = view.findViewById(R.id.home_topic_list);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(),topicImages,topicTitles,topicDescriptions);
        homeRecyclerView.setAdapter(adapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
