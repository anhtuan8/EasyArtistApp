package ie.app.easyartistapp.ui.personal.favourite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Article;

public class PersonalFavouriteFragment extends Fragment {
    private FavouriteViewModel favouriteViewModel;
    private static final String TAG = "PersonalFavouriteFragme";

    private ArrayList<Article> favoriteArticleList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_personal_favourite,container,false);

        try {
            favouriteViewModel = new FavouriteViewModel(getContext());
        } catch (IOException e) {
            e.printStackTrace();
        }

        favouriteViewModel.getmFavoriteArticleList().observe(getViewLifecycleOwner(), new Observer<HashMap<String,Article>>() {
            @Override
            public void onChanged(HashMap<String, Article> stringArticleHashMap) {
                favoriteArticleList = new ArrayList<>();
                for(String key: stringArticleHashMap.keySet()){
                    favoriteArticleList.add(stringArticleHashMap.get(key));
                }
                initRecyclerView(root);
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initRecyclerView(View view){
//        Log.d(TAG, "initRecyclerView: " + favouriteViewModel.getFavoriteArticleList().get(1).getName());
        RecyclerView favouriteRecyclerView = view.findViewById(R.id.personalFavouriteList);
        FavouriteRecyclerViewAdapter adapter = new FavouriteRecyclerViewAdapter(getContext(),favoriteArticleList);
        favouriteRecyclerView.setAdapter(adapter);
        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
