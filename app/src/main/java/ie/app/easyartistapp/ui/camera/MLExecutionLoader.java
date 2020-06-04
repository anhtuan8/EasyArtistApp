package ie.app.easyartistapp.ui.camera;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.FutureTarget;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MLExecutionLoader extends AsyncTaskLoader<Bitmap> {

    private static final String TAG = "IOException" ;
    private String mcontentPath = null;
    private String mstylePath = null;
    private StyleTransferModelExecutor styleTransferModelExecutor = null;
    @Override
    protected void onStartLoading() {
        super.onStartLoading();

        forceLoad();
    }

    public MLExecutionLoader(@NonNull Context context, String contentPath, String stylePath) {
        super(context);
        mcontentPath = contentPath;
        mstylePath = stylePath;
        try {
            styleTransferModelExecutor = new StyleTransferModelExecutor((Activity) context, StyleTransferModelExecutor.Device.CPU);
        }catch (IOException io){
            Log.d(TAG, "IO File");
        }

    }

    @Nullable
    @Override
    public Bitmap loadInBackground() {
        String realPath = getImgCachePath(mstylePath);
        Bitmap bitmap = styleTransferModelExecutor.execute(mcontentPath,realPath, getContext());
        return bitmap;
    }

    private String getImgCachePath(String url) {
        FutureTarget<File> futureTarget = Glide.with(getContext()).load(url).downloadOnly(100, 100);
        try {
            File file = futureTarget.get();
            String path = file.getAbsolutePath();
            return path;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }
}
