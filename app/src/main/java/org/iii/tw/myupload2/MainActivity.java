package org.iii.tw.myupload2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private  File sdroot;

    private ProgressDialog pDialog;
    private UIHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdroot = Environment.getExternalStorageDirectory();

        pDialog = new ProgressDialog(this);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.setTitle("Downloading.......");

        handler = new UIHandler();
    }

    public  void newFile(View v) {
        File newFile = new File(sdroot.getAbsolutePath() + "/chuan.txt");
        try {
            FileOutputStream fout = new FileOutputStream(newFile);
            fout.write("Hello chuan".getBytes());
            fout.flush();
            fout.close();
            Toast.makeText(this, "Write ok", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void upload(View v){
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    MultipartUtility mu = new MultipartUtility("http://10.0.3.2/upload.php","UTF-8");
                    mu.addFilePart("upload",new File(sdroot.getAbsolutePath()+"/"));
                    mu.finish();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void btn1(View v){
        pDialog.show();
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    MultipartUtility mu =
                            new MultipartUtility("http://m.coa.gov.tw/OpenData/FarmerMarketData.aspx","UTF-8");
//                    mu.addFilePart("upload",new File(sdroot.getAbsolutePath()+"/"));
                    List<String> ret = mu.finish();
                    for(String line:ret){
                        Log.v("brad",line.length() + ":" + line);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public void camera(View v){
        Intent it = new Intent(this,MyCameraActivity.class);
        startActivity(it);
    }

    private class UIHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            pDialog.dismiss();
        }
    }
}
