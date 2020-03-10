package com.iti.intake40.tripista;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context arg0, Intent arg1) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(arg1.getAction())) {
            setIntent(arg0, arg1);
        } else {
            setIntent(arg0, arg1);
        }
    }

    public void setIntent(Context arg0, Intent arg1) {
        Intent alarmIntent = new Intent(arg0, AlertActivity.class);
        alarmIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        arg0.startActivity(alarmIntent);

    }
}
