package ie.app.easyartistapp;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class EasyArtistApplication extends Application {
    private static final String TAG = "EasyArtistApplication";

    private ArrayList<String> favoriteList;
    private String favoriteFile;

    @Override
    public void onCreate() {
        super.onCreate();
        favoriteFile = "favorite.txt";
        try {
//            favoriteList = new ArrayList<>();
//            writeToInternalFile(favoriteList);
            favoriteList = readFavoriteListFromInternalStorage(favoriteFile);
        } catch (IOException e) {
            favoriteList = new ArrayList<>();
            e.printStackTrace();
        }
    }

    public void addFavoriteList(String articleId) throws IOException {
        favoriteList.add(articleId);
        writeToInternalFile(articleId);
    }

    public void removeFromFavoriteList(String articleId) throws IOException {
        favoriteList.remove(articleId);
        writeToInternalFile(favoriteList);
    }

    private void writeToInternalFile(String articleId) throws IOException {
        FileOutputStream outputStream;
        outputStream = this.openFileOutput(favoriteFile, Context.MODE_APPEND);
        String string = articleId +"\n";
        outputStream.write(string.getBytes());
        outputStream.close();
        Log.d(TAG, "writeToInternalFile: write completed");
    }

    private void writeToInternalFile(ArrayList<String> articleIds) throws IOException{
        FileOutputStream outputStream;
        outputStream = this.openFileOutput(favoriteFile, Context.MODE_PRIVATE);
        for(String id : articleIds) {
            String string = id + "\n";
            outputStream.write(string.getBytes());
        }
        outputStream.close();
        Log.d(TAG, "write multiple ids ToInternalFile: write completed");
    }

    private ArrayList<String> readFavoriteListFromInternalStorage(String filename) throws IOException {
        ArrayList<String> articleIds = new ArrayList<>();
        FileInputStream inputStream;
        try {
            inputStream = this.openFileInput(filename);
        }
        catch (FileNotFoundException e){
            System.out.println("File doesn't exist");
            return new ArrayList<String>();
        }
        DataInputStream in = new DataInputStream(inputStream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine;
        //Đọc từng dòng
        while ((strLine = br.readLine()) != null) {
            articleIds.add(strLine);
        }
        in.close();
        return articleIds;
    }

    public String getFavoriteFile(){
        return favoriteFile;
    }

    public ArrayList<String> getFavoriteList() {
        return favoriteList;
    }
}
