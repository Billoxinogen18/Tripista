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
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;


public class AlertActivity extends Activity {
    Uri notification;
    int notificationId = 0;
    Ringtone r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_alert);
        notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
        r = RingtoneManager.getRingtone(this.getApplicationContext(), notification);
        r.play();
        displayAlert();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        r.play();
    }

    private void displayAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Trip ").setCancelable(
                false).setPositiveButton("Snooze",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        show_Notification();
                        r.stop();
                    }
                }).setNegativeButton("Stop",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        r.stop();
                        NotificationManager notificationManager = (NotificationManager)
                                getSystemService(Context.
                                        NOTIFICATION_SERVICE);
                        //   notificationManager.cancelAll();
                        notificationManager.cancel(notificationId);
                        finish();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @TargetApi(Build.VERSION_CODES.O)
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)

    public void show_Notification() {
        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
        Notification notification = new Notification.Builder(getApplicationContext(), CHANNEL_ID)
                .setContentText("You are waiting for your trip")
                .setContentTitle("Trip away")
                .setContentIntent(pendingIntent)
                .setChannelId(CHANNEL_ID)
                .setSmallIcon(android.R.drawable.sym_action_chat)
                .setOngoing(true)
                .build();

        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.createNotificationChannel(notificationChannel);
        notificationManager.notify(notificationId, notification);
        finish();

    }

    @Override
    protected void onPause() {
        super.onPause();
        r.stop();


    }


}
