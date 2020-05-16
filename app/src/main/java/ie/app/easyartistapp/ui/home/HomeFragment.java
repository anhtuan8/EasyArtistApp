package ie.app.easyartistapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Article;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private ArrayList<Article> articleList = new ArrayList<>();
//
//    private ArrayList<String> articleImages = new ArrayList<>();
//    private ArrayList<String> articleTitles = new ArrayList<>();
//    private ArrayList<String> articleDescriptions = new ArrayList<>();

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView: started");
//        final TextView topicName = root.findViewById(R.id.topicName);
        //set topicName = topicName from HomeViewModel (implement later)
//        topicName.setText("Bui Xuan Phai");
        homeViewModel.getmArticles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                for (Article a : articles) {
                    articleList.add(new Article(a.getArticle_id(),a.getDescription(),a.getDetail(),a.getImage_link(),a.getName(),a.getTopic_id(),a.getTopic_name()));
                }
                initImageBitmaps(root);
            }

        });

        return root;
    }

    /**
     * Next version: implement an Article class, store in HomeViewModel and get Images, Titles, Descriptions from ViewModel
     * Next next version: popular and recommend RecyclerList
     * @param view
     */
    private void initImageBitmaps(View view){
        Log.d(TAG, "initImageBitmaps: started");

        initRecyclerView(view);
    }

    private void initRecyclerView(View view){
        Log.d(TAG, "initRecycleView: started");
        Log.v(TAG, getActivity().getPackageName());
        RecyclerView homeRecyclerView = view.findViewById(R.id.home_article_list);
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(), articleList);
        homeRecyclerView.setAdapter(adapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

    }
}
