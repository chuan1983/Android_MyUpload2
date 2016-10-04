package org.iii.tw.myupload2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MyCameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_camera);

        checkPermission(Manifest.permission.CAMERA,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.RECORD_AUDIO);
        int num = Camera.getNumberOfCameras();
        Log.v("brad","Camera"+num);
    }

    private int checkCameraNumber(){
        return Camera.getNumberOfCameras();
    }

//    權限設定
    private void checkPermission(String... permissions){
        for (String permission : permissions){
            if (ContextCompat.checkSelfPermission(this,
                permission) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{permission},1);
            }
        }
    }
}
