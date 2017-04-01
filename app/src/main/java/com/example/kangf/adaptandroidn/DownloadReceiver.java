package com.example.kangf.adaptandroidn;

import android.app.DownloadManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.support.v7.app.NotificationCompat;
import android.text.TextUtils;


import java.io.File;

import static android.app.PendingIntent.getActivity;


/**
 * Created by kangf on 2017/3/31.
 */

public class DownloadReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            System.out.println("hola: complete.");
            String filename = FileNameUtil.fileName;
            if (!TextUtils.isEmpty(filename)) {
                showCompleteNotification(context, new File(filename));
            }

        }
        if (intent.getAction().equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            System.out.println("hola: clicked.");
        }
    }

    private void showCompleteNotification(Context context, File file) {

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // 创建一个开启安装App界面的意图
        Intent installIntent = new Intent();
        installIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        installIntent.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //by default, applicationId == packageName
            Uri apkUri =
                    FileProvider.getUriForFile(context,
                            context.getApplicationContext().getPackageName() + ".fileprovider",
                            file);
            installIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            installIntent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            installIntent.setDataAndType(Uri.fromFile(file),
                    "application/vnd.android.package-archive");
        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
        builder.setAutoCancel(false)//通知设置不会自动显示
                .setShowWhen(true)//显示时间
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("title here")
                .setContentText("click to install");

        PendingIntent pendingIntent = getActivity(context, 0, installIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        notification.defaults = Notification.DEFAULT_SOUND;// 设置为默认的声音
        notification.flags = Notification.FLAG_ONLY_ALERT_ONCE | Notification.FLAG_NO_CLEAR;
        manager.notify(32423, notification);// 显示通知, random id.
    }
}
