package ie.app.easyartistapp.ui.ArticleInATopic;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Article;
import ie.app.easyartistapp.entityObject.Topic;
import ie.app.easyartistapp.ui.home.HomeRecyclerViewAdapter;
import ie.app.easyartistapp.ui.topic.TopicRecyclerViewAdapter;
import ie.app.easyartistapp.ui.topic.TopicViewModel;

public class ArticlesInATopicActivity extends AppCompatActivity {
    private ArticlesInATopicViewModel articlesInATopicViewModel;

    private String topic_id = "2pt2cKwVW8aP3wgfrd38yu";
    private String topic_name = "Topic name";

    private RecyclerView recyclerView;
    private TextView textView;

    private ArrayList<Topic> topics = new ArrayList<>();

    private static final String TAG = "HomeFragment";

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic);

        Bundle bundle = getIntent().getExtras();
        assert bundle != null;
        Log.d(TAG, "savedInstanceState " + bundle.getString("topicId") );

        if(bundle.getString("topicId") != null) {
            topic_id = bundle.getString("topicId");
        }
        if(bundle.getString("topicName") != null){
            topic_name = bundle.getString("topicName");
        }
        articlesInATopicViewModel = new ArticlesInATopicViewModel(topic_id);

        recyclerView = findViewById(R.id.topic_list);
        textView = findViewById(R.id.title);
        textView.setText(topic_name);
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        articlesInATopicViewModel.getmArticles().observe(this, new Observer<ArrayList<Article>>() {
            @Override
            public void onChanged(ArrayList<Article> articles) {
                initRecyclerView(articles);
            }
        });
    }

    private void initRecyclerView(ArrayList<Article> articles){
        Log.d(TAG, "initRecycleView: started");
        HomeRecyclerViewAdapter adapter = new HomeRecyclerViewAdapter(this, articles);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
