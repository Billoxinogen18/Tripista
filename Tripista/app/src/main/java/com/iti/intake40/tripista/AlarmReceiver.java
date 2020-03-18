package com.iti.intake40.tripista;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import androidx.annotation.RequiresApi;

import com.google.firebase.FirebaseApp;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.Calendar;
import java.util.List;
import java.util.Random;


public class AlarmReceiver extends BroadcastReceiver {

    FireBaseCore core = FireBaseCore.getInstance();
    String date;
    String time;
    String[] myformat;
    String []myformat2;
    Context context;
    Intent intent;

    public int notificationId = new Random().nextInt(10000);
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        FirebaseApp.initializeApp(arg0);
        if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
             context = arg0;
             intent = arg1;
            core.getTripsForCurrentUser(new OnTripsLoaded() {
                @Override
                public void onTripsLoaded(List<Trip> trips) {
                    //receive all upcoming trips in list
                    //loop the list for all the trips
                    for (Trip t : trips) {
                        //get the date & time for each trip
                        String[] dateArr = getDate();
                        String[] timeArr = getTime();
                        Log.i("date", "date" + t.getDate());
                        Log.i("date", "date" + t.getTime());
                        Calendar myAlarmDate = Calendar.getInstance();
                        myAlarmDate.set(Integer.parseInt(dateArr[2]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[0]), Integer.parseInt(timeArr[2]) , Integer.parseInt(timeArr[1]),Integer.parseInt(timeArr[0]));
                        AlarmManager tripAlarmManager =(AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
                        Intent tripAlarmIntent = new Intent(context, AlertActivity.class);
                        PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(context, notificationId,tripAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
                        tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);
                        String tripTitle = intent.getStringExtra("title");
                        int tripNotificationId = intent.getExtras().getInt("s");
                        String tripId = intent.getStringExtra("tripId");
                        Intent alarmIntent = new Intent(context, AlertActivity.class);
                        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                        alarmIntent.putExtra("s", tripNotificationId);
                        alarmIntent.putExtra("title", tripTitle);
                        alarmIntent.putExtra("tripId", tripId);
                        context.startActivity(alarmIntent);
                        //set alarm

//                        setTripAlarm(t.getDate(), t.getTime());
                        //if the trip is round get the back date & time
//                        if (t.getType() == Trip.Type.ROUND_TRIP) {
//                            setTripAlarm(t.setBacDate(), t.getTime());
//                        }
                    }
                }
            });

        } else {

            String tripTitle = arg1.getStringExtra("title");
            int tripNotificationId = arg1.getExtras().getInt("s");
            String tripId = arg1.getStringExtra("tripId");
            Intent alarmIntent = new Intent(arg0, AlertActivity.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_MULTIPLE_TASK);

            alarmIntent.putExtra("s", tripNotificationId);
            alarmIntent.putExtra("title", tripTitle);
            alarmIntent.putExtra("tripId", tripId);
            arg0.startActivity(alarmIntent);
        }
//
    }

    public String[]  getDate() {
         myformat = date.split("-");
        System.out.println(myformat[0]);
        System.out.println(myformat[1]);
        System.out.println(myformat[2]);
        return myformat;
    }

     public String[]  getTime(){
        myformat2= time.split(":");
        System.out.println(myformat2[0]);
        System.out.println(myformat2[1]);
        System.out.println(myformat2[2]);
        return myformat2;
    }
    public void setAlarm(){

    }

    }



