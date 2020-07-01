package ie.app.easyartistapp.ui.topic;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;
import ie.app.easyartistapp.entityObject.Topic;;

public class TopicActivity extends AppCompatActivity {
    private TopicViewModel topicViewModel;

    private RecyclerView recyclerView;

    private ArrayList<Topic> topics = new ArrayList<>();

    private static final String TAG = "HomeFragment";

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    public void onCreate(@NonNull Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        topicViewModel = new TopicViewModel();
        setContentView(R.layout.activity_topic);
        recyclerView = findViewById(R.id.topic_list);
        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        }
        topicViewModel.getmTopics().observe(this, (Observer<ArrayList<Topic>>) this::initRecyclerView);
    }

    private void initRecyclerView(ArrayList<Topic> topics){
        Log.d(TAG, "initRecycleView: started");
        TopicRecyclerViewAdapter adapter = new TopicRecyclerViewAdapter(this, topics);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2,RecyclerView.VERTICAL,false));
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
