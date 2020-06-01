package ie.app.easyartistapp.ui.camera;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ImageView;

import ie.app.easyartistapp.R;

public class StyleImageActivity extends AppCompatActivity{

    private ImageView imageView = null;
    private final String ACTION_GALLERY = "ie.app.easyartistapp.ui.camera.ACTION_GALLERY";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_style_image);
        imageView = findViewById(R.id.imageView);

        Intent intent = getIntent();
        if( intent != null){
            String uuid = intent.getExtras().getString("UUID");
            if(uuid.equals(ACTION_GALLERY)){
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }else{
                String picturePath = intent.getExtras().getString(MediaStore.EXTRA_OUTPUT);
                imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            }
        }

    }
}
