package com.example.lab3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Button startButton;
    private volatile boolean stopThread = false;
    TextView myTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myTextView = findViewById(R.id.downloadProgress);
        startButton = findViewById(R.id.startButton);
    }

    public void mockFileDownloader(){
        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                startButton.setText("DOWNLOADING....");
            }
        });

        for(int downloadProgress = 0; downloadProgress <= 100; downloadProgress=downloadProgress+10 ){
            if (stopThread) {
                int finalDownloadProgress = downloadProgress;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run(){
                        startButton.setText("Start");
                        myTextView.setText(" ");
                    }

                });
                return;
            }
            Log.d(TAG, "Download Progress: " + downloadProgress + "%");

            try{
                Thread.sleep(1000);
            } catch (InterruptedException e){
                e.printStackTrace();
            }

            int finalDownloadProgress1 = downloadProgress;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    myTextView.setText("Download Progress: " + finalDownloadProgress1 + "%");
                }
            });

        }

        runOnUiThread(new Runnable(){
            @Override
            public void run(){
                startButton.setText("Start");
            }
        });
    }

    public void startDownload(View view){
        stopThread = false;
        ExampleRunnable runnable = new ExampleRunnable();
        new Thread(runnable).start();
    }
    public void stopDownload(View view){
        stopThread = true;
    }

    class ExampleRunnable implements Runnable{
        @Override
        public void run() {
            mockFileDownloader();
        }
    }
}

