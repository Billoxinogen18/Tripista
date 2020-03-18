package com.iti.intake40.tripista;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import java.util.Random;
import java.util.UUID;


public class AlertActivity extends Activity {
    NotificationManager notificationManager;
    Uri notification;
    Ringtone ringtone;
    Intent intent;
    String intentExtra;
 public int strExtra = 0;
 String tripIdExtra;
    public int notificationId = new Random().nextInt(10000);



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_alert);

        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY|
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON,
                WindowManager.LayoutParams.FLAG_FULLSCREEN |
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                        WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED |
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON |
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        intent = getIntent();
        intentExtra = intent.getStringExtra("title");
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        ringtone = RingtoneManager.getRingtone(this.getApplicationContext(), notification);
        ringtone.play();
        displayAlert();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        ringtone.play();
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void displayAlert() {
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Trip ").setCancelable(
                false).setPositiveButton("Snooze",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        id = notificationId;
                        show_Notification();
                        ringtone.stop();
                    }


                }).setNegativeButton("Stop",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        id = notificationId;
                        NotificationManager notificationManager = (NotificationManager)
                                getSystemService(Context.
                                        NOTIFICATION_SERVICE);
                        notificationManager.cancel(id);
                        dialog.cancel();
                        ringtone.stop();
                        finish();

                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.setCanceledOnTouchOutside(false);
        alert.show();

    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void show_Notification() {

        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
        String CHANNEL_ID = generateString();
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),notificationId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("You are waiting for your trip")
                .setContentTitle(intentExtra)
                .setContentIntent(pendingIntent)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon((BitmapFactory.decodeResource(this.getResources(),
                        R.mipmap.ic_launcher_foreground)))
                .setChannelId(CHANNEL_ID)
                .setOngoing(true)
                .setAutoCancel(true)
                .build();

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(notificationId, notification);
        finish();

    }

    @Override
    protected void onStop() {
        super.onStop();
        ringtone.stop();
//        finish();
    }
    public static String generateString() {
        String uuid = UUID.randomUUID().toString();
        return "uuid = " + uuid;
    }
}


