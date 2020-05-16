/**
 * ViewModel cho Personal Favorite
 * 1. Cần hàm lấy danh sách các bài viết đã quan tâm:
 * @param: articleIdList lưu trong file ở local.
 * ArrayList<Article> getFavorites(ArrayList<String> favoriteArticleIdList)
 * 2. Hàm đọc các Id bài favourite đã lưu ở local: favoriteArticleIdList:
 * ArrayList<String> getFavoriteArticleIdList()
 */
package ie.app.easyartistapp.ui.personal.favourite;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import ie.app.easyartistapp.entityObject.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavouriteViewModel {
    private ArrayList<String> titles = new ArrayList<>();
    private ArrayList<String> images = new ArrayList<>();
    private FirebaseFirestore easyArtistDb;

    public FavouriteViewModel(){
//        titles.add("Buc tranh 1");
//        images.add("https://kenh14cdn.com/thumb_w/640/Images/Uploaded/Share/2012/03/09/e06120309kpvangoghava.jpg");
//        titles.add("Buc tranh 2");
//        images.add("https://i.pinimg.com/originals/33/fc/95/33fc959336bbeec077b0f4daceffc891.jpg");
//        titles.add("Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3Buc tranh 3");
//        images.add("https://www.wallpaperflare.com/static/431/740/850/the-divine-comedy-dante-s-inferno-dante-alighieri-gustave-dor%C3%A9-wallpaper.jpg");
        String favor_file = "abcd";
        try {
            getFavoriteArticle(favor_file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getFavoriteArticle(String favor_file) throws FileNotFoundException {
        File f = new File(favor_file);
        ArrayList<String> list_id = new ArrayList<>();
        BufferedReader bufferIn = null;
        try{
            String favor_id;
            bufferIn = new BufferedReader(new FileReader(f));
            while ((favor_id = bufferIn.readLine()) != null){
                list_id.add(favor_id);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                bufferIn.close();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        easyArtistDb = FirebaseFirestore.getInstance();
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        for (String s: list_id){
            Query query = artistRef.whereEqualTo("article_id",s);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Article art = document.toObject(Article.class);
                            titles.add(art.getName());
                            images.add(art.getImage_link());
                            Log.d(TAG, "onComplete: article title " + art.getName());
                        }

                    } else {
                        Log.d("data", "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<String> getTitles() {
        return titles;
    }
}
