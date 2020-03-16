package com.iti.intake40.tripista;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ShowMap extends AppCompatActivity {
    private static final int SYSTEM_ALERT_WINDOW_PERMISSION = 2084;
    private String end, start;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_map);
        if (getIntent() != null) {
            end = getIntent().getExtras().getString("end");
            start = getIntent().getExtras().getString("start");
            //"http://maps.google.com/maps?saddr="+start+"&daddr="+end)
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("google.navigation:q="+end)).setPackage("com.google.android.apps.maps");
            startActivity(mapIntent);
        }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && !Settings.canDrawOverlays(this)) {
                askPermission();
            }



            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                startService(new Intent(ShowMap.this, FloatingWidgetService.class));
                finish();

            } else if (Settings.canDrawOverlays(ShowMap.this)) {
                startService(new Intent(ShowMap.this, FloatingWidgetService.class));
                finish();
                //finish();
            } else {
                askPermission();
                Toast.makeText(ShowMap.this, "You need System Alert Window Permission to do this", Toast.LENGTH_SHORT).show();
            }


        }


        private void askPermission ()
        {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION, Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, SYSTEM_ALERT_WINDOW_PERMISSION);
        }
    }
