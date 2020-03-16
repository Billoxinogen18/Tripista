package com.iti.intake40.tripista;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
//
//public class AlarmReceiver extends BroadcastReceiver {
//    int RQS;
//    Date date;
////    private FireBaseCore core = FireBaseCore.getInstance();
//
////    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    @Override
//    public void onReceive(Context arg0, Intent arg1) {
////        if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
////             core.getTripsForCurrentUser(new OnTripsLoaded() {
////                 @Override
////                 public void onTripsLoaded(List<Trip> trips) {
////
////                 }
////             });
////            Calendar myAlarmDate = Calendar.getInstance();
////            myAlarmDate.set(2020, 2, 15, 16, 21
////                    , 0);
////            AlarmManager tripAlarmManager = (AlarmManager) arg0.getSystemService(Context.ALARM_SERVICE);
////            Intent tripAlarmIntent = new Intent(arg0, AlertActivity.class);
////            PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(arg0, 0, tripAlarmIntent, 0);
////            tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);
////            Toast.makeText(arg0,
////                    "Invalid Date/Time",
////                    Toast.LENGTH_LONG).show();
////
////
////        } else {
//        Toast.makeText(arg0,
//                    "Invalid Date/Time",
//                    Toast.LENGTH_LONG).show();
//
//            Intent alarmIntent = new Intent(arg0, AlertActivity.class);
//            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//            arg0.startActivity(alarmIntent);
//        }
//
//
// //   }
//
//}
public class AlarmReceiver extends BroadcastReceiver {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
//            Intent alarm = new Intent(arg0, AlertActivity.class);
//       alarm.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//        arg0.startActivity(alarm);
////
///////////////////////
//            Calendar myAlarmDate = Calendar.getInstance();
//            myAlarmDate.set(2020, 2, 15, 16 , 21
//                    ,0);
//            AlarmManager tripAlarmManager =(AlarmManager)arg0.getSystemService(Context.ALARM_SERVICE);
//            Intent tripAlarmIntent = new Intent(arg0, AlertActivity.class);
//            PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(arg0, 0,tripAlarmIntent, 0);
//            tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);



        } else {
           if(arg1.getExtras().getString("end")!=null && arg1.getExtras().getString("start")!=null) {
               String end = arg1.getExtras().getString("end");
               String start = arg1.getExtras().getString("start");
               Intent alarmIntent = new Intent(arg0, AlertActivity.class);
               alarmIntent.addFlags(Intent.FLAG_ACTIVITY_MULTIPLE_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
               alarmIntent.putExtra("start",start);
               alarmIntent.putExtra("end",end);
               arg0.startActivity(alarmIntent);
           }
        }
//| Intent.FLAG_ACTIVITY_MULTIPLE_TASK
    }

}