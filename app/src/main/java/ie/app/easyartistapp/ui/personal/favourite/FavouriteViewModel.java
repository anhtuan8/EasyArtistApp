/**
 * ViewModel cho Personal Favorite
 * 1. Cần hàm lấy danh sách các bài viết đã quan tâm:
 * @param: articleIdList lưu trong file ở local.
 * ArrayList<Article> getFavorites(ArrayList<String> favoriteArticleIdList)
 * 2. Hàm đọc các Id bài favourite đã lưu ở local: favoriteArticleIdList:
 * ArrayList<String> getFavoriteArticleIdList()
 */
package ie.app.easyartistapp.ui.personal.favourite;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class FavouriteViewModel {
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();

    public FavouriteViewModel(){
        titles.add("Buc tranh 1");
        images.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
        titles.add("Buc tranh 2");
        images.add("https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg");
        titles.add("Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3");
        images.add("https://www.wallpaperflare.com/static/431/740/850/the-divine-comedy-dante-s-inferno-dante-alighieri-gustave-dor%C3%A9-wallpaper.jpg");
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }
}
