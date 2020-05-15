package ie.app.easyartistapp.ui.personal.favourite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import ie.app.easyartistapp.R;

public class PersonalFavouriteFragment extends Fragment {
    private FavouriteViewModel favouriteViewModel = new FavouriteViewModel();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_favourite,container,false);
        initRecyclerView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void initRecyclerView(View view){
        RecyclerView favouriteRecyclerView = view.findViewById(R.id.personalFavouriteList);
        FavouriteRecyclerViewAdapter adapter = new FavouriteRecyclerViewAdapter(getContext(),favouriteViewModel);
        favouriteRecyclerView.setAdapter(adapter);
        favouriteRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
