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

import java.util.Calendar;
import java.util.Date;

public class AlarmReceiver extends BroadcastReceiver {
    int RQS;
    Date date;
    private FireBaseCore core;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {

            Calendar myAlarmDate = Calendar.getInstance();
            myAlarmDate.set(2020, 2, 15, 16, 21
                    , 0);
            AlarmManager tripAlarmManager = (AlarmManager) arg0.getSystemService(Context.ALARM_SERVICE);
            Intent tripAlarmIntent = new Intent(arg0, AlertActivity.class);
            PendingIntent tripAlarmPendingIntent = PendingIntent.getActivity(arg0, 0, tripAlarmIntent, 0);
            tripAlarmManager.setExact(AlarmManager.RTC_WAKEUP, myAlarmDate.getTimeInMillis(), tripAlarmPendingIntent);
            Toast.makeText(arg0,
                    "Invalid Date/Time",
                    Toast.LENGTH_LONG).show();


        } else {
            Intent alarmIntent = new Intent(arg0, AlertActivity.class);
            alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            arg0.startActivity(alarmIntent);
        }


    }

}
}