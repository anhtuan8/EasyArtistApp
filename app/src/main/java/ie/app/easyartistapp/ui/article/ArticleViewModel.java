/**
 * ViewModel cho activity đọc Article.
 * Cần: String articleContent, String articleTitle, String topicTitle, ArrayList<String> tagNames.
 * Cần viết hàm lấy các data trên từ Firebase theo articleId
 */

package ie.app.easyartistapp.ui.article;

import android.text.Html;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
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

import static androidx.constraintlayout.widget.Constraints.TAG;

public class ArticleViewModel extends ViewModel {
    final String articleId;
    private FirebaseFirestore easyArtistDb;
    MutableLiveData<String> articleContent;
    MutableLiveData<String> articleTitle;
    MutableLiveData<String> topicTitle;
    MutableLiveData<ArrayList<String>> tagNames;

    public ArticleViewModel(String articleId){
        articleContent = new MutableLiveData<>();
//        articleContent.setValue("<h1>Article content</h1><p>Article content Article content Article content</p><img src=\"https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg\"/>");

        articleTitle = new MutableLiveData<>();
//        articleTitle.setValue("The Last Supper Painting");

        topicTitle = new MutableLiveData<>();
//        topicTitle.setValue("Renaissance");

        tagNames = new MutableLiveData<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Renaissance");
        tags.add("Classic painting");
        tags.add("Realism");
        tagNames.setValue(tags);
        this.articleId = articleId;
        getArticle();
    }

    public void getArticle(){
        easyArtistDb = FirebaseFirestore.getInstance();
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        Query query = artistRef.whereEqualTo("article_id", this.articleId);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Article art = document.toObject(Article.class);
                        articleContent.setValue(art.getDetail());
                        articleTitle.setValue(art.getName());
                        topicTitle.setValue(art.getTopic_name());
                        Log.d(TAG, "onComplete: article title " + art.getName());
                    }

                } else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public LiveData<String> getArticleContent() {
        return articleContent;
    }

    public LiveData<ArrayList<String>> getTagNames() {
        return tagNames;
    }

    public LiveData<String> getArticleTitle() {
        return articleTitle;
    }

    public LiveData<String> getTopicTitle() {
        return topicTitle;
    }
}
