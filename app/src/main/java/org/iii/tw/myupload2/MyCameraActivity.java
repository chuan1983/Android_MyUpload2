package org.iii.tw.myupload2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

public class MyCameraActivity extends AppCompatActivity {

    private Camera camera;
    private FrameLayout frame;
    private CameraPreView preView;

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

        camera = Camera.open();
        Camera.Parameters params = camera.getParameters();
//        camera.takePicture(new MyShutter(),null,new MyJpegCallback());
//        camera.release();

        frame = (FrameLayout) findViewById(R.id.activity_my_camera);
        frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                camera.takePicture(new MyShutter(),null,new MyJpegCallback());
            }
        });
        preView = new CameraPreView(this,camera);
        frame.addView(preView);
    }
    private class MyShutter implements Camera.ShutterCallback{
        @Override
        public void onShutter() {

        }
    }

    private class MyJpegCallback implements Camera.PictureCallback{
        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }
    }

    private int checkCameraNumber(){
//            PackageManager packageManager = getPackageManager();
//          if (packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA)){
//
//          }
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
