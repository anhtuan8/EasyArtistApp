package ie.app.easyartistapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public HomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
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