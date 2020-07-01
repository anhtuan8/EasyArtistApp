package ie.app.easyartistapp.ui.personal.gallery;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class PersonalGalleryFragment extends Fragment {
    private GalleryViewModel galleryViewModel = new GalleryViewModel();
    private ArrayList<String> imagePaths = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_personal_gallery,container,false);
        //imagePaths = galleryViewModel.getImagePaths();
        createPathList();
        initRecyclerView(root);
        return root;
    }

    private void createPathList(){
        imagePaths.add("ie/app/easyartistapp/images/1.png");
        imagePaths.add("ie/app/easyartistapp/images/2.png");
        imagePaths.add("ie/app/easyartistapp/images/3.png");
        imagePaths.add("ie/app/easyartistapp/images/4.png");
        imagePaths.add("ie/app/easyartistapp/images/5.png");
        imagePaths.add("ie/app/easyartistapp/images/6.png");
        imagePaths.add("ie/app/easyartistapp/images/7.png");
        imagePaths.add("ie/app/easyartistapp/images/8.png");
        imagePaths.add("ie/app/easyartistapp/images/9.png");
        imagePaths.add("ie/app/easyartistapp/images/10.png");
        imagePaths.add("ie/app/easyartistapp/images/11.png");
        imagePaths.add("ie/app/easyartistapp/images/12.png");
        imagePaths.add("ie/app/easyartistapp/images/13.png");
    }

    public void initRecyclerView(View view){
        RecyclerView recyclerView = view.findViewById(R.id.personal_gallery);
        GalleryRecyclerViewAdapter adapter = new GalleryRecyclerViewAdapter(getContext(),imagePaths);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 5));
    }
}
