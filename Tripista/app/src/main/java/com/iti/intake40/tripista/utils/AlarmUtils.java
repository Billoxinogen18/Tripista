package com.iti.intake40.tripista.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.iti.intake40.tripista.AlarmReceiver;
import com.iti.intake40.tripista.AlertActivity;
import com.iti.intake40.tripista.OnTripsLoaded;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class AlarmUtils {
    public static String date;
    public static String time;
    public static String[] myformat;
    public static String[] myformat2;
    public static Context context;
    public static String[] dateArr = getDate();
    public static String[] timeArr = getTime();

    public static int notificationId;

    public static void setMultipleAlarms() {
        FireBaseCore core = FireBaseCore.getInstance();
        core.getTripsForCurrentUser(new OnTripsLoaded() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onTripsLoaded(List<Trip> trips) {
                //receive all upcoming trips in list
                //loop the list for all the trips
                for (Trip t : trips) {
                    //get the date & time for each trip
                    if (t.getStatus() == Trip.Status.UPCOMMING && t.getType() == Trip.Type.ONE_WAY) {
                        date = t.getDate();
                        time = t.getTime();
                        notificationId = t.getCancelID();
                        //set alarm
                        setAlarm(notificationId, t.getTitle());
                    }
                    //if the trip is round get the back date & time
                    if (t.getStatus() == Trip.Status.UPCOMMING && t.getType() == Trip.Type.ROUND_TRIP) {
                        date = t.getDate();
                        time = t.getTime();
                        notificationId = t.getCancelID();
                        //set alarm
                        setAlarm(notificationId, t.getTitle());
                        date = t.getBackDate();
                        time = t.getBackTime();
                        notificationId = t.getBackCancelID();
                        //set alarm
                        setSecAlarm(notificationId, t.getTitle());
                    }
                }
            }

        });
    }

    public static void clearAlarm(int alarmId) {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }

    public static String[] getDate() {
        myformat = date.split("-");
        System.out.println(myformat[0]);
        System.out.println(myformat[1]);
        System.out.println(myformat[2]);
        return myformat;
    }

    public static String[] getTime() {
        myformat2 = time.split(":");
        System.out.println(myformat2[0]);
        System.out.println(myformat2[1]);
        System.out.println(myformat2[2]);
        return myformat2;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setAlarm(int id, String str) {
        dateArr = getDate();
        timeArr = getTime();
        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]), Integer.parseInt(timeArr[0]), Integer.parseInt(timeArr[1]), Integer.parseInt(timeArr[2]));
        AlarmManager tripAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent tripAlarmIntent = new Intent(context, AlertActivity.class);
        tripAlarmIntent.putExtra("title", str);
        PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(context, id, tripAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setSecAlarm(int id, String str) {
        dateArr = getDate();
        timeArr = getTime();
        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.set(Integer.parseInt(dateArr[0]), Integer.parseInt(dateArr[1]) - 1, Integer.parseInt(dateArr[2]), Integer.parseInt(timeArr[0]) + 12, Integer.parseInt(timeArr[1]), Integer.parseInt(timeArr[2]));
        AlarmManager tripAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent tripAlarmIntent = new Intent(context, AlertActivity.class);
        PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(context, id, tripAlarmIntent, 0);
        tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);

    }

}
