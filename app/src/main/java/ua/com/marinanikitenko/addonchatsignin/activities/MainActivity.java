package ua.com.marinanikitenko.addonchatsignin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;


import ua.com.marinanikitenko.addonchatsignin.R;
import ua.com.marinanikitenko.addonchatsignin.model.Constants;
import ua.com.marinanikitenko.addonchatsignin.utils.PermissionListener;
import ua.com.marinanikitenko.addonchatsignin.utils.PermissionUtil;

import static ua.com.marinanikitenko.addonchatsignin.R.id.permission_camera;
import static ua.com.marinanikitenko.addonchatsignin.R.id.permission_local_storage;
import static ua.com.marinanikitenko.addonchatsignin.R.id.permission_location;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, PermissionListener {
    private String TAG = MainActivity.class.getSimpleName() + ":";
    private Button auth;
    private Button chat;
    private Button requestCamera;
    private Button requestLocation;
    private Button requestReadStorage;
    private PermissionUtil permissionUtil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews(){

        auth = (Button)findViewById(R.id.auth);
        chat = (Button)findViewById(R.id.chat);
        requestCamera = (Button)findViewById(permission_camera);
        requestLocation = (Button)findViewById(permission_location);
        requestReadStorage = (Button)findViewById(permission_local_storage);

        requestLocation.setOnClickListener(this);
        requestCamera.setOnClickListener(this);
        chat.setOnClickListener(this);
        auth.setOnClickListener(this);
        requestReadStorage.setOnClickListener(this);

        permissionUtil = new PermissionUtil(MainActivity.this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.auth:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;

            case R.id.chat:
                //goToChat();
                break;

            case R.id.permission_camera:
                permissionUtil = new PermissionUtil(MainActivity.this);
                permissionUtil.setListener(MainActivity.this);
                permissionUtil.checkPermissions(Constants.PERMISSION_REQUEST_CAMERA);
                break;

            case R.id.permission_location:
                permissionUtil = new PermissionUtil(MainActivity.this);
                permissionUtil.setListener(MainActivity.this);
                permissionUtil.checkPermissions(Constants.PERMISSION_REQUEST_LOCATION);
                break;

            case R.id.permission_local_storage:
                permissionUtil = new PermissionUtil(MainActivity.this);
                permissionUtil.setListener(MainActivity.this);
                permissionUtil.checkPermissions(Constants.PERMISSION_READ_EXTERNAL_STORAGE);
                break;
        }
    }



    @Override
    public void onPermissionGranted(int requestCode) {}

    @Override
    public void onPermissionDenied(int requestCode) {}

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionUtil.getOnRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}
