package com.iti.intake40.tripista;


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
import android.os.Bundle;
import android.view.WindowManager;
import androidx.appcompat.app.AlertDialog;


public class AlertActivity extends Activity {
    Uri notification;
    int notificationId = 0;
    Ringtone r;


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

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void displayAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your Trip ").setCancelable(
                false).setPositiveButton("Start", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(AlertActivity.this,ShowMap.class));
                finish();
            }
        }).setNeutralButton("Snooze",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        show_Notification();
                        r.stop();
                    }
                }).setNegativeButton("Stop",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                        r.stop();
                        NotificationManager notificationManager = (NotificationManager)
                                getSystemService(Context.
                                        NOTIFICATION_SERVICE);
                        //   notificationManager.cancelAll();
                        notificationManager.cancel(notificationId);
                       dialog.cancel();
                        android.os.Process.killProcess(android.os.Process.myPid());
                        finish();


                    }
                }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
//        alert.setCanceledOnTouchOutside(false);
        alert.show();


    }


    public void show_Notification() {
        Intent intent = new Intent(getApplicationContext(), AlertActivity.class);
        String CHANNEL_ID = "MYCHANNEL";
        NotificationChannel notificationChannel = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            notificationChannel = new NotificationChannel(CHANNEL_ID, "name", NotificationManager.IMPORTANCE_LOW);
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
        }else
        {
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, 0);
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentText("You are waiting for your trip")
                    .setContentTitle("Trip away")
                    .setContentIntent(pendingIntent)
                    .setSmallIcon(android.R.drawable.sym_action_chat)
                    .setOngoing(true)
                    .build();

            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId, notification);
        }
        finish();

    }

//    /*  start floating widget service  */
//    public void createFloatingWidget(View view) {
//        //Check if the application has draw over other apps permission or not?
//        //This permission is by default available for API<23. But for API > 23
//        //you have to ask for the permission in runtime.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
//            //If the draw over permission is not available open the settings screen
//            //to grant the permission.
//            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
//                    Uri.parse("package:" + getPackageName()));
//            startActivityForResult(intent, 100);
//        } else
//            //If permission is granted start floating widget service
//            startFloatingWidgetService();
//
//    }
//
//    /*  Start Floating widget service and finish current activity */
//    private void startFloatingWidgetService() {
//        startService(new Intent(AlertActivity.this, FloatingWidgetService.class));
//        finish();
//    }
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == 100) {
//            //Check if the permission is granted or not.
//            if (resultCode == RESULT_OK)
//                //If permission granted start floating widget service
//                startFloatingWidgetService();
//            else
//                //Permission is not available then display toast
//                Toast.makeText(this,
//                        "denied",
//                        Toast.LENGTH_SHORT).show();
//
//        } else {
//            super.onActivityResult(requestCode, resultCode, data);
//        }
//    }
    @Override
    protected void onStop() {
        super.onStop();
        r.stop();
    }



}
