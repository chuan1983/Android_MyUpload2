package org.iii.tw.myupload2;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private  File sdroot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sdroot = Environment.getExternalStorageDirectory();
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
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    MultipartUtility mu =
                            new MultipartUtility("http://m.coa.gov.tw/OpenData/FarmerMarketData.aspx","UTF-8");
                    mu.addFilePart("upload",new File(sdroot.getAbsolutePath()+"/"));
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
}
