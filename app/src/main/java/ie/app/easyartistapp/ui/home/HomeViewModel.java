package ie.app.easyartistapp.ui.home;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import ie.app.easyartistapp.EasyArtistApplication;
import ie.app.easyartistapp.entityObject.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> mArticles;
    private MutableLiveData<ArrayList<Boolean>> mArticlesIsFavorite;
    private ArrayList<Boolean> articlesIsFavorite = new ArrayList<>();
    private ArrayList<Article> articles = new ArrayList<>();
    private FirebaseFirestore easyArtistDb;
    private Context context;

    public HomeViewModel(Context context) {
        mArticles = new MutableLiveData<>();
        mArticlesIsFavorite = new MutableLiveData<>();
        this.context = context;
        getHomeViewArticlesFromDB();
    }

    /**
     * Get all list articles for home view
     * @param
     */
    public void getHomeViewArticlesFromDB(){
        Log.d(TAG, "getArticlesFromDB: called");
        easyArtistDb = FirebaseFirestore.getInstance();
        final CollectionReference artistRef = easyArtistDb.collection("articles");
        artistRef.orderBy("article_id").limit(10).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Article art = document.toObject(Article.class);
                        Activity activity = (Activity) context;
                        EasyArtistApplication myApp = (EasyArtistApplication) activity.getApplication();
                        if(myApp.getFavoriteList().contains(art.getArticle_id())){
                            art.setFavorite(true);
                            articlesIsFavorite.add(Boolean.TRUE);
                        }
                        else{
                            art.setFavorite(false);
                            articlesIsFavorite.add(Boolean.FALSE);
                        }
                        articles.add(art);
                    }
                    mArticles.setValue(articles);
                    mArticlesIsFavorite.setValue(articlesIsFavorite);
                    Log.d(TAG, "onComplete: mArticles size " + articles.size());
                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public MutableLiveData<ArrayList<Article>> getmArticles() {
        return mArticles;
    }

    public MutableLiveData<ArrayList<Boolean>> getmArticlesIsFavorite() {
        return mArticlesIsFavorite;
    }

    public void setmArticlesFavorite(boolean isFavorite, int position) {
        articlesIsFavorite.set(position,isFavorite);
        mArticlesIsFavorite.setValue(articlesIsFavorite);
    }

    public void setContext(Context context) {
        this.context = context;
    }
}