/**
 * ViewModel cho Personal Favorite
 * 1. Cần hàm lấy danh sách các bài viết đã quan tâm:
 * @param: articleIdList lưu trong file ở local.
 * ArrayList<Article> getFavorites(ArrayList<String> favoriteArticleIdList)
 * 2. Hàm đọc các Id bài favourite đã lưu ở local: favoriteArticleIdList:
 * ArrayList<String> getFavoriteArticleIdList()
 */
package ie.app.easyartistapp.ui.personal.favourite;

import android.content.Context;
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

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ie.app.easyartistapp.entityObject.Article;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FavouriteViewModel {
    private static final String TAG = "FavouriteViewModel";

    private MutableLiveData<HashMap<String, Article>> mFavoriteArticleList;
    private HashMap<String, Article> favoriteArticleList;
    private ArrayList<String> articleIds;
    private FirebaseFirestore easyArtistDb;
    private Context context;

    public FavouriteViewModel(Context context) throws IOException {
        mFavoriteArticleList = new MutableLiveData<>();
        favoriteArticleList = new HashMap<>();
        articleIds = new ArrayList<>();
        this.context = context;

        String favor_file = "favorite.txt";

//        writeToInternalFile(favor_file);
        readFavoriteListFromInternalStorage(favor_file);
    }

    private void writeToInternalFile(String filename) throws IOException {
        ArrayList<String> ids = new ArrayList<>();
        FileOutputStream outputStream;
        outputStream = context.openFileOutput(filename, Context.MODE_APPEND);
        ids.add("WNKAhWB8Qv6iFSGMCXpd3o");
        ids.add("fLFCaCBBoXic3uVLYDRWCc");
        ids.add("ZjKJqCDfb3iBL8nVvforFy");
        ids.add("NjQTiY27W4vhH59dTSDJWK");
        ids.add("UoqmMj5vYozzpB2t8qxop3");
        ids.add("cUbu7FesS8fLUCeauzFtFV");
        ids.add("2UnTSJCMewxo7jf5pcnbCN");
        ids.add("X2a4vRcLvg4UttXakRXuLe");
        ids.add("ZjKJqCDfb3iBL8nVvforFy");
        ids.add("7mpu84qBkRJD94uWMxPthq");
        ids.add("5omSbA2P9HD3GBTXzTbAW3");
        ids.add("Qduy3z2VSqLeBA4EgEQUCS");
        ids.add("WYtgwHzdxo2JdsKoJpxPaf");
        ids.add("NjQTiY27W4vhH59dTSDJWK");
        for(String id : ids){
            String string = id +"\n";
            outputStream.write(string.getBytes());
        }
        outputStream.close();
        Log.d(TAG, "writeToInternalFile: write completed");
    }

    private void readFavoriteListFromInternalStorage(String filename) throws IOException {
        FileInputStream inputStream;
        inputStream = context.openFileInput(filename);
        DataInputStream in = new DataInputStream(inputStream);
        BufferedReader br = new BufferedReader(
                new InputStreamReader(in));
        String strLine;
        //Đọc từng dòng
        while ((strLine = br.readLine()) != null) {
            articleIds.add(strLine);
        }
        in.close();
        getFavoriteArticle();
    }

    private void getFavoriteArticle() {
        easyArtistDb = FirebaseFirestore.getInstance();
        final CollectionReference artistRef = easyArtistDb
                .collection("articles");
        for (String s: articleIds){
            Query query = artistRef.whereEqualTo("article_id",s);
            query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                @Override
                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Article art = document.toObject(Article.class);
                            favoriteArticleList.putIfAbsent(art.getArticle_id(),art);
                            Log.d(TAG, "onComplete: article title last " + art.getName());
                            mFavoriteArticleList.setValue(favoriteArticleList);
                        }
                    } else {
                        Log.d(TAG, "Error getting documents: ", task.getException());
                    }
                }
            });
        }
    }

    public MutableLiveData<HashMap<String, Article>> getmFavoriteArticleList() {
        return mFavoriteArticleList;
    }
}
