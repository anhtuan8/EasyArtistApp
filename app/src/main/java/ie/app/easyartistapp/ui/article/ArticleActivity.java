package ie.app.easyartistapp.ui.article;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.sufficientlysecure.htmltextview.HtmlHttpImageGetter;
import org.sufficientlysecure.htmltextview.HtmlTextView;

import java.io.IOException;
import java.util.ArrayList;

import ie.app.easyartistapp.EasyArtistApplication;
import ie.app.easyartistapp.R;

public class ArticleActivity extends AppCompatActivity {
    private static final String TAG = "ArticleActivity";
    private ArticleViewModel articleViewModel;
    private TextView articleTitle, topicTitle;
    private HtmlTextView articleContent;
    private ImageView articleImage;
    private ImageButton favoriteButton;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getIntent().getExtras();
        Log.d(TAG, "savedInstanceState " + bundle.getString("articleId") );
        if(bundle.getString("articleId") != null) {
            articleViewModel = new ArticleViewModel(bundle.getString("articleId"), this);
        }
        else {
            articleViewModel = new ArticleViewModel("0", this);
        }
        setContentView(R.layout.activity_home_article);

        articleTitle = findViewById(R.id.articleTitle);
        topicTitle = findViewById(R.id.topicTitle);
        articleContent = findViewById(R.id.articleContent);
        articleImage = findViewById(R.id.articleImage);
        favoriteButton = findViewById(R.id.imageButton);

        Toolbar toolbar = findViewById(R.id.appbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHideOnContentScrollEnabled(true);
        }

        articleViewModel.getIsFavorite().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean isFavorite) {
                if(isFavorite){
                    favoriteButton.setImageResource(R.drawable.ic_star_black_48dp);
                    favoriteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favoriteButton.setImageResource(R.drawable.ic_star_border_black_48dp);
                            articleViewModel.setIsFavorite(Boolean.FALSE);
                            EasyArtistApplication myApp = (EasyArtistApplication) getApplication();
                            try {
                                myApp.removeFromFavoriteList(articleViewModel.getArticleId());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),"Remove " + articleViewModel.getArticleTitle().getValue() +" from favorite list",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else {
                    favoriteButton.setImageResource(R.drawable.ic_star_border_black_48dp);
                    favoriteButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            favoriteButton.setImageResource(R.drawable.ic_star_black_48dp);
                            articleViewModel.setIsFavorite(Boolean.TRUE);
                            EasyArtistApplication myApp = (EasyArtistApplication) getApplication();
                            try {
                                myApp.addFavoriteList(articleViewModel.getArticleId());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(getApplicationContext(),"Add " + articleViewModel.getArticleTitle().getValue() +" to favorite list",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

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

        articleViewModel.getArticleImage().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Glide.with(getApplicationContext()).asBitmap().load(s).into(articleImage);
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

