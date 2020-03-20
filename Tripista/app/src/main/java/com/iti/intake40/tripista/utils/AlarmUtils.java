package com.iti.intake40.tripista.utils;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.iti.intake40.tripista.AlarmReceiver;
import com.iti.intake40.tripista.AlertActivity;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.Calendar;
import java.util.List;

import static android.content.Context.ALARM_SERVICE;
import static com.facebook.FacebookSdk.getApplicationContext;

public class AlarmUtils {
    public static Context context;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setMultipleAlarms(List<Trip> trips) {
        String date = null;
        String time = null;
        int notificationId;
        //receive all upcoming trips in list
        //loop the list for all the trips
        for (Trip t : trips) {
            //get the date & time for each trip
            if (t.getStatus() == Trip.Status.UPCOMMING && t.getType() == Trip.Type.ONE_WAY) {
                date = t.getDate();
                time = t.getTime();
                notificationId = t.getCancelID();
                //set alarm
                setAlarm(notificationId, t.getTitle(), DateUtils.getDateArr(date), DateUtils.getTimeArr(time));
            }
            //if the trip is round get the back date & time
            if (t.getStatus() == Trip.Status.UPCOMMING && t.getType() == Trip.Type.ROUND_TRIP) {
                date = t.getDate();
                time = t.getTime();
                notificationId = t.getCancelID();
                //set alarm
                setAlarm(notificationId, t.getTitle(), DateUtils.getDateArr(date), DateUtils.getTimeArr(time));

                date = t.getBackDate();
                time = t.getBackTime();
                notificationId = t.getBackCancelID();
                //set alarm
                setAlarm(notificationId, t.getTitle(), DateUtils.getDateArr(date), DateUtils.getTimeArr(time));
            } else if (t.getStatus() == Trip.Status.IN_PROGRESS && t.getType() == Trip.Type.ROUND_TRIP) {
                date = t.getBackDate();
                time = t.getBackTime();
                notificationId = t.getBackCancelID();
                //set alarm
                setAlarm(notificationId, t.getTitle(), DateUtils.getDateArr(date), DateUtils.getTimeArr(time));
            }
        }
    }


    public static void clearAlarm(int alarmId) {
        AlarmManager alarmManager = (AlarmManager) getApplicationContext().getSystemService(ALARM_SERVICE);
        Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),
                alarmId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.cancel(pendingIntent);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void setAlarm(int alarmId, String alarmTitle,
                                String[] dateArr, String[] timeArr) {

        Calendar myAlarmDate = Calendar.getInstance();
        myAlarmDate.set(Integer.parseInt(dateArr[0]),
                Integer.parseInt(dateArr[1]) - 1,
                Integer.parseInt(dateArr[2]),
                Integer.parseInt(timeArr[0]),
                Integer.parseInt(timeArr[1]),
                Integer.parseInt(timeArr[2]));
        AlarmManager tripAlarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent tripAlarmIntent = new Intent(context, AlertActivity.class);
        tripAlarmIntent.putExtra("title", alarmTitle);
        PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(context, alarmId,
                tripAlarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);
    }

}
