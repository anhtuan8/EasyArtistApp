package ie.app.easyartistapp.ui.article;

import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.util.ArrayList;

import ie.app.easyartistapp.R;

public class ArticleActivity extends AppCompatActivity {
    private ArticleViewModel articleViewModel;
    private TextView articleTitle, topicTitle;
    private HtmlTextView articleContent;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        articleViewModel = new ArticleViewModel();
        setContentView(R.layout.activity_home_article);

        articleTitle = findViewById(R.id.articleTitle);
        topicTitle = findViewById(R.id.topicTitle);
        articleContent = findViewById(R.id.articleContent);

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setHomeButtonEnabled(true);

        articleViewModel.getArticleContent().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                articleContent.setHtml(s,new HtmlHttpImageGetter(articleContent));
            }
        });

        articleViewModel.getArticleTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                articleTitle.setText(s);
            }
        });

        articleViewModel.getTopicTitle().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                topicTitle.setText(s);
            }
        });

        articleViewModel.getTagNames().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                initTagList(strings);
            }
        });
    }

    private void initTagList(ArrayList<String> tags){
        RecyclerView tagList = findViewById(R.id.articleTags);
        ArticleTagsRecyclerViewAdapter adapter = new ArticleTagsRecyclerViewAdapter(this, tags);
        tagList.setAdapter(adapter);
        tagList.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
    }

}

