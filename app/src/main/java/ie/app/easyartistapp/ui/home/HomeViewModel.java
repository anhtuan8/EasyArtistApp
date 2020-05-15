package ie.app.easyartistapp.ui.home;

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

import ie.app.easyartistapp.entityObject.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Article>> mArticles;
    private ArrayList<Article> articles = new ArrayList<>();
    private FirebaseFirestore easyArtistDb;
    private FirebaseStorage storage;

    public HomeViewModel() {
        mArticles = new MutableLiveData<>();
        getArticlesFromDB("Bui Xuan Phai");
    }

    /**
     * Get article list by topic name
     * @param name_topic
     */
    public void getArticlesFromDB(String name_topic){
        Log.d(TAG, "getArticlesFromDB: called");
        easyArtistDb = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        Query query = artistRef.whereEqualTo("topic_name", name_topic);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Article art = document.toObject(Article.class);
                        articles.add(art);
                    }
                    mArticles.setValue(articles);
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

    public class TopicItem{
        String topicImage, topicTitle, topicDescription;
        public TopicItem(String image, String title, String description){
            this.topicDescription = description;
            this.topicTitle = title;
            this.topicImage = image;
        }
    }
}