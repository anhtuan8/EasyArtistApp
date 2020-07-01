package ie.app.easyartistapp;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
<<<<<<< HEAD
import android.view.View;
import android.widget.Toast;
import android.view.Window;
=======
<<<<<<< HEAD
import android.view.View;
=======
import android.view.Window;
>>>>>>> remotes/origin/layout-tuan
>>>>>>> camera-design-layout
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import ie.app.easyartistapp.ui.AboutActivity;
import ie.app.easyartistapp.ui.camera.ContentCameraActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
<<<<<<< HEAD
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.core.app.ActivityCompat;
=======
<<<<<<< HEAD
import androidx.core.app.ActivityCompat;
=======
import androidx.drawerlayout.widget.DrawerLayout;
>>>>>>> remotes/origin/layout-tuan
>>>>>>> camera-design-layout
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import ie.app.easyartistapp.ui.camera.ContentCameraActivity;
import ie.app.easyartistapp.ui.topic.TopicActivity;

public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";

    private static final int PERMISSION_REQUEST_CAMERA = 0;
    private ActionBarDrawerToggle actionBarToggle;

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.appbar_menu,menu);
        return true;
    }

    FloatingActionButton camera_fab = null;
    private View main_layout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: called");
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
        setContentView(R.layout.activity_main);
<<<<<<< HEAD

=======
<<<<<<< HEAD
>>>>>>> camera-design-layout
        CurvedBottomNavigationView curvedBottomNavigationView = findViewById(R.id.nav_view);
        main_layout = findViewById(R.id.nav_host_fragment);
        curvedBottomNavigationView.inflateMenu(R.menu.bottom_nav_menu);
       // BottomNavigationView navView = findViewById(R.id.nav_view);
<<<<<<< HEAD
=======
=======

        BottomNavigationView navView = findViewById(R.id.nav_view);

>>>>>>> remotes/origin/layout-tuan
>>>>>>> camera-design-layout
        Toolbar toolbar = (Toolbar)findViewById(R.id.appbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer);

//         Passing each menu ID as a set of Ids because each
//         menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
<<<<<<< HEAD
                R.id.navigation_home, R.id.navigation_personal)
                .setOpenableLayout(drawer)
=======
<<<<<<< HEAD
                R.id.navigation_home, R.id.navigation_personal)
=======
                R.id.navigation_home, R.id.navigation_camera, R.id.navigation_personal)
                .setDrawerLayout(drawer)
>>>>>>> remotes/origin/layout-tuan
>>>>>>> camera-design-layout
                .build();
        actionBarToggle = new ActionBarDrawerToggle(this,
                drawer,
                toolbar,
                R.string.nav_app_bar_open_drawer_description,
                R.string.nav_app_bar_close_drawer_description);
        drawer.addDrawerListener(actionBarToggle);

        NavigationView drawerNavView = findViewById(R.id.drawer_nav_view);
        drawerNavView.setNavigationItemSelectedListener(this);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(toolbar, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(toolbar, navController, appBarConfiguration);
<<<<<<< HEAD
        NavigationUI.setupWithNavController(curvedBottomNavigationView,navController);
=======
<<<<<<< HEAD
        NavigationUI.setupWithNavController(curvedBottomNavigationView,navController);
=======
        NavigationUI.setupWithNavController(navView,navController);

>>>>>>> remotes/origin/layout-tuan
>>>>>>> camera-design-layout
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
//            actionBar.setHomeAsUpIndicator(R.drawable.ic_dehaze_black_24dp);
//            actionBar.setHideOnContentScrollEnabled(true);
        }
        Log.v("Toolbar", String.valueOf(toolbar.getMenu().size()));

        camera_fab = findViewById(R.id.camera_fab);
        camera_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCameraPreview();
            }
        });
    }

    private void showCameraPreview() {
        // BEGIN_INCLUDE(startCamera)
        // Check if the Camera permission has been granted
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, start camera preview
            startCamera();
        } else {
            // Permission is missing and must be requested.
            requestCameraPermission();
        }
        // END_INCLUDE(startCamera)
    }

    private void startCamera() {
        Intent intent = new Intent(this, ContentCameraActivity.class);
        startActivity(intent);
    }

    private void requestCameraPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with cda button to request the missing permission.
            Snackbar.make(main_layout, R.string.camera_access_required,
                    Snackbar.LENGTH_INDEFINITE).setAction(R.string.permission_ok, new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.CAMERA},
                            PERMISSION_REQUEST_CAMERA);
                }
            }).show();

        } else {
            Snackbar.make(main_layout, R.string.camera_unavailable, Snackbar.LENGTH_SHORT).show();
//            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA}, PERMISSION_REQUEST_CAMERA);
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        // BEGIN_INCLUDE(onRequestPermissionsResult)
        if (requestCode == PERMISSION_REQUEST_CAMERA) {
            // Request for camera permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start camera preview Activity.
                Snackbar.make(main_layout, R.string.camera_permission_granted,
                        Snackbar.LENGTH_SHORT)
                        .show();
                startCamera();
            } else {
                // Permission request was denied.
                Snackbar.make(main_layout, R.string.camera_permission_denied,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
        // END_INCLUDE(onRequestPermissionsResult)
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        if(actionBarToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()){
            case R.id.appbarbutton_search:
                //search event
                Toast.makeText(this,"Entering search event",Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        actionBarToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_about:{
                //start about activity
                Intent intent = new Intent(this, AboutActivity.class);
                startActivity(intent);
                return true;
            }
            case R.id.nav_topic:{
                //start topic activity
                Intent intent = new Intent(this, TopicActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"Topic menu selected",Toast.LENGTH_SHORT).show();
            }
        }
        Log.v(TAG, "onNavigationItemSelected: called. Menu item: " + item.getTitle());
        return true;
    }
}
