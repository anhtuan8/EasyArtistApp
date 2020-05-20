/**
 * ViewModel cho Personal Favorite
 * 1. Cần hàm lấy danh sách các bài viết đã quan tâm:
 * @param: articleIdList lưu trong file ở local.
 * ArrayList<Article> getFavorites(ArrayList<String> favoriteArticleIdList)
 * 2. Hàm đọc các Id bài favourite đã lưu ở local: favoriteArticleIdList:
 * ArrayList<String> getFavoriteArticleIdList()
 */
package ie.app.easyartistapp.ui.personal.favourite;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ie.app.easyartistapp.EasyArtistApplication;
import ie.app.easyartistapp.entityObject.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavouriteViewModel {
    private static final String TAG = "FavouriteViewModel";

    private MutableLiveData<HashMap<String, Article>> mFavoriteArticleList;
    private HashMap<String, Article> favoriteArticleList;
    private ArrayList<String> articleIds;
    private FirebaseFirestore easyArtistDb;
    private Context context;

    public FavouriteViewModel(Context context) throws IOException {
        mFavoriteArticleList = new MutableLiveData<>();
        favoriteArticleList = new HashMap<>();
        articleIds = new ArrayList<>();
        this.context = context;

        Activity activityFromContext = (Activity) context;
        EasyArtistApplication myApp = (EasyArtistApplication)activityFromContext.getApplication();

//        writeToInternalFile(favor_file);
//        readFavoriteListFromInternalStorage(myApp.getFavoriteFile());
        articleIds = myApp.getFavoriteList();
        getFavoriteArticle();
    }

    private void getFavoriteArticle() {
        easyArtistDb = FirebaseFirestore.getInstance();
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        for (String s: articleIds){
            Query query = artistRef.whereEqualTo("article_id",s);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Article art = document.toObject(Article.class);
                            favoriteArticleList.putIfAbsent(art.getArticle_id(),art);
                            Log.d(TAG, "onComplete: article title last " + art.getName());
                            mFavoriteArticleList.setValue(favoriteArticleList);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    public MutableLiveData<HashMap<String, Article>> getmFavoriteArticleList() {
        return mFavoriteArticleList;
    }
}
