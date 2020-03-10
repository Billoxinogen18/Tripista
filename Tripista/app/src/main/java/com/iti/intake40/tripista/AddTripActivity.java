package com.iti.intake40.tripista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.icu.text.TimeZoneFormat;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity {
    private TextView text;

    private Button addTripBtn;
    private FireBaseCore core;
    private String name;
    private Button timeBtn;
    private Button dateBtn;
    public Trip tripModel;
    DatePickerDialog datePicker;
    TimePickerDialog timePicker;
    private int mYear, mMonth, mDay, hour, minute;
    Calendar cal;
    Calendar now;
    Calendar current;
    final static int RQS_1 = 1;
    String TAG = "place";
    public String startPlace;
    public String endPlace;
    public String time;
    public String date;
    public String strDate;
    public String strTime;
    public String  coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_trip);
        addTripBtn = findViewById(R.id.addTrip);
        text = findViewById(R.id.Name);
        timeBtn = findViewById(R.id.timeBtn);
        dateBtn = findViewById(R.id.dateBtn);
        tripModel = new Trip();

//        tripModel = new Trip();
        cal = Calendar.getInstance();
        now = Calendar.getInstance();
        current = Calendar.getInstance();


        // Initialize Places.

        String apiKey = "AIzaSyDIHrWWuzN2st31DRm6G9KnULCEKSpcV-A";
        Places.initialize(getApplicationContext(), apiKey);
        // Create a new Places client instance.
        PlacesClient placesClient = Places.createClient(this);

        if (!Places.isInitialized()) {
            Places.initialize(getApplicationContext(), "YOUR_API_KEY");
        }


        // Initialize the AutocompleteSupportFragment.

        AutocompleteSupportFragment startAutoCompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.startfragment);

        AutocompleteSupportFragment endAutoCompleteFragment = (AutocompleteSupportFragment)
                getSupportFragmentManager().findFragmentById(R.id.endfragment);


//StartAutoComplete
        startAutoCompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
        startAutoCompleteFragment.setCountry("Eg");
        startAutoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println(place.getName());

                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                startPlace = place.getName();
//        LatLng cor = place.getLatLng();
//          coordinates = String.valueOf(cor.latitude);
////               coordinates = String.valueOf(place.getLatLng().latitude).concat(" ").concat(String.valueOf(place.getLatLng().longitude));




            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);

            }


        });

//EndAutoComplete
        endAutoCompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
        endAutoCompleteFragment.setCountry("Eg");
        endAutoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                System.out.println(place.getName());
                Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
                endPlace = place.getName();

            }

            @Override
            public void onError(@NonNull Status status) {
                Log.i(TAG, "An error occurred: " + status);


            }
        });

//Get Date
        dateBtn = findViewById(R.id.dateBtn);
        dateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date date = Calendar.getInstance().getTime();
                DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                strDate = dateFormat.format(date);
                mYear = cal.get(Calendar.YEAR);
                mMonth = cal.get(Calendar.MONTH);
                mDay = cal.get(Calendar.DAY_OF_MONTH);

                datePicker = new DatePickerDialog(AddTripActivity.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                cal.set(year, monthOfYear, dayOfMonth);
                            }
                        }, mYear, mMonth, mDay);
                datePicker.show();

            }
        });
//Get Time
        timeBtn = findViewById(R.id.timeBtn);
        timeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date time = Calendar.getInstance().getTime();
                DateFormat timeFormat = new SimpleDateFormat("hh:mm");
                strTime = timeFormat.format(time);

                hour = cal.get(Calendar.HOUR_OF_DAY);
                minute = cal.get(Calendar.MINUTE);

                timePicker = new TimePickerDialog(AddTripActivity.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {
                                cal.set(Calendar.HOUR_OF_DAY, hour);
                                cal.set(Calendar.MINUTE, minute);
                            }
                        }, hour, minute, false);
                timePicker.show();

            }

        });
//addTrip
        addTripBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                core = FireBaseCore.getInstance();
                name = text.getText().toString();
                tripModel.setTitle(name);
                tripModel.setStartPoint(startPlace);
                tripModel.setEndPoint(endPlace);
                tripModel.setDate(strDate);
                tripModel.setTime(strTime);
//                tripModel.setLatLng(coordinates);
                core.addTrip(tripModel);

                if (cal.compareTo(current) <= 0) {
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();

                } else {
                    setAlarm(cal);
                }
            }
        });

    }

    private void setAlarm(Calendar targetCal) {
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
    }
}