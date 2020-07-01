package ie.app.easyartistapp.ui.ArticleInATopic;

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

import java.util.ArrayList;

import ie.app.easyartistapp.entityObject.Article;
import ie.app.easyartistapp.entityObject.Topic;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ArticlesInATopicViewModel extends ViewModel {
    MutableLiveData<ArrayList<Article>> mArticles;
    FirebaseFirestore easyArtistDb;
    private String topic_id;

    public ArticlesInATopicViewModel(String topic_id){
        mArticles = new MutableLiveData<>();
        this.topic_id = topic_id;
        getArticlesInATopic(topic_id);
    }

    public void getArticlesInATopic(String topic_id){
        Log.d(TAG, "getArticlesFromDB: called");
        easyArtistDb = FirebaseFirestore.getInstance();
        final ArrayList<Article> articles = new ArrayList<>();
        final CollectionReference articlesRef = easyArtistDb
                .collection("articles");
        Query query = articlesRef.whereEqualTo("topic_id", topic_id);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Article art = document.toObject(Article.class);
                    articles.add(art);
                }
                mArticles.setValue(articles);
                Log.d(TAG, "onComplete: mArticles size " + articles.size());
            }
        });
    }

    public MutableLiveData<ArrayList<Article>> getmArticles() {
        return mArticles;
    }
}
