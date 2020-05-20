package ie.app.easyartistapp.ui.home;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import ie.app.easyartistapp.EasyArtistApplication;
import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Article;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;

    private RecyclerView recyclerView;

    private ArrayList<Article> articleList = new ArrayList<>();
    private ArrayList<Boolean> articleIsFavorite = new ArrayList<>();

    private static final String TAG = "HomeFragment";
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel = new HomeViewModel(getContext());
        final View root = inflater.inflate(R.layout.fragment_home, container, false);

        Log.d(TAG, "onCreateView: started");

        recyclerView = root.findViewById(R.id.home_article_list);

        homeViewModel.getmArticles().observe(getViewLifecycleOwner(), new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                for (Article a : articles) {
                    articleList.add(new Article(a.getArticle_id(),a.getDescription(),a.getDetail(),a.getImage_link(),a.getName(),a.getTopic_id(),a.getTopic_name(),a.isFavorite()));
                }
                initImageBitmaps(root);
            }
        });

//        homeViewModel.getmArticlesIsFavorite().observe(getViewLifecycleOwner(), new Observer<ArrayList<Boolean>>() {
//            @Override
//            public void onChanged(final ArrayList<Boolean> articleIsFavorites) {
//                for(int i=0 ; i<articleIsFavorites.size() ; i++){
//                    final int finalI = i;
//                    HomeViewHolder viewHolder = (HomeViewHolder)recyclerView.findViewHolderForLayoutPosition(finalI);
//                    assert viewHolder != null;
//                    final ImageButton favoriteButton = viewHolder.getFavoriteButton();
//                    //article at position i is favorite
//                    if(articleIsFavorites.get(finalI)){
//                        favoriteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //Click to remove from favorite
//                                favoriteButton.setImageResource(R.drawable.ic_star_border_black_48dp);
//                                homeViewModel.setmArticlesFavorite(false,finalI);
//                                EasyArtistApplication myApp = (EasyArtistApplication) getActivity().getApplication();
//                                try {
//                                    myApp.removeFromFavoriteList(homeViewModel.getmArticles().getValue().get(finalI).getArticle_id());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                    //article is not favorite
//                    else {
//                        favoriteButton.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                //Click to add to favorite
//                                favoriteButton.setImageResource(R.drawable.ic_star_black_48dp);
//                                homeViewModel.setmArticlesFavorite(true,finalI);
//                                EasyArtistApplication myApp = (EasyArtistApplication) getActivity().getApplication();
//                                try {
//                                    myApp.addFavoriteList(homeViewModel.getmArticles().getValue().get(finalI).getArticle_id());
//                                } catch (IOException e) {
//                                    e.printStackTrace();
//                                }
//                            }
//                        });
//                    }
//                }
//            }
//        });

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
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(getContext(),this, articleList);
        homeRecyclerView.setAdapter(adapter);
        homeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public HomeViewModel getHomeViewModel() {
        return homeViewModel;
    }
}
