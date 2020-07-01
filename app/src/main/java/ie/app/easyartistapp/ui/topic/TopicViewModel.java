package ie.app.easyartistapp.ui.topic;

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

public class TopicViewModel extends ViewModel {
    MutableLiveData<ArrayList<Topic>> mTopics;
    FirebaseFirestore easyArtistDb;
    String typeOfTopic;

    public TopicViewModel(){
        mTopics = new MutableLiveData<>();
        getAllTopics();
    }

    public TopicViewModel(String typeOfTopic){
        mTopics = new MutableLiveData<ArrayList<Topic>>();
        this.typeOfTopic = typeOfTopic;
        getTopics(typeOfTopic);
    }

    public void getAllTopics(){
        easyArtistDb = FirebaseFirestore.getInstance();
        final ArrayList<Topic> topics = new ArrayList<>();
        final CollectionReference topicsRef = easyArtistDb
                .collection("topics");
        topicsRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Topic t = document.toObject(Topic.class);
                        topics.add(t);
                    }
                    mTopics.setValue(topics);
                    Log.d(TAG, "onComplete: mArticles size " + topics.size());
                }else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void getTopics(String typeOfTopic){
        Log.d(TAG, "getArticlesFromDB: called");
        easyArtistDb = FirebaseFirestore.getInstance();
        final ArrayList<Topic> topics = new ArrayList<>();
        final CollectionReference topicsRef = easyArtistDb
                .collection("topics");
        Query query = topicsRef.whereEqualTo("type", typeOfTopic);
        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Topic t = document.toObject(Topic.class);
                        topics.add(t);
                    }
                    mTopics.setValue(topics);
                    Log.d(TAG, "onComplete: mArticles size " + topics.size());
                }else {
                    Log.d("data", "Error getting documents: ", task.getException());
                }
            }
        });
    }

    public void setTypeOfTopic(String typeOfTopic) {
        this.typeOfTopic = typeOfTopic;
    }

    public MutableLiveData<ArrayList<Topic>> getmTopics() {
        return mTopics;
    }
}
