/**
 * ViewModel cho activity đọc Article.
 * Cần: String articleContent, String articleTitle, String topicTitle, ArrayList<String> tagNames.
 * Cần viết hàm lấy các data trên từ Firebase theo articleId
 */

package ie.app.easyartistapp.ui.article;

import android.text.Html;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class ArticleViewModel extends ViewModel {
    final String articleId;
    MutableLiveData<String> articleContent;
    MutableLiveData<String> articleTitle;
    MutableLiveData<String> topicTitle;
    MutableLiveData<ArrayList<String>> tagNames;

    public ArticleViewModel(String articleId){
        articleContent = new MutableLiveData<>();
        articleContent.setValue("<h1>Article content</h1><p>Article content Article content Article content</p><img src=\"https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg\"/>");

        articleTitle = new MutableLiveData<>();
        articleTitle.setValue("The Last Supper Painting");

        topicTitle = new MutableLiveData<>();
        topicTitle.setValue("Renaissance");

        tagNames = new MutableLiveData<>();
        ArrayList<String> tags = new ArrayList<>();
        tags.add("Renaissance");
        tags.add("Classic painting");
        tags.add("Realism");
        tagNames.setValue(tags);
        this.articleId = articleId;
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
