package com.example.kangf.adaptandroidn;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.view.View;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private String downloadUrl = "http://d.gsxservice.com/app/genshuixue.apk?ct=http://d.gsxservice.com/app/genshuixue.apk?ct=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onTestNotify(View view) {
//        addNotificaction();
    }


    public void onNotification(View view) {
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setTitle("downloading...");
        request.setDescription("desc for the file.");

        double suffix = Math.random() * 1000;
        String fileSuffix = String.valueOf((int) suffix);
        FileNameUtil.fileName = Environment.getExternalStorageDirectory() + "/boomstack_demo" + fileSuffix + ".apk";

        File saveFile = new File(FileNameUtil.fileName);
        request.setDestinationUri(Uri.fromFile(saveFile));

        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);
    }
}
