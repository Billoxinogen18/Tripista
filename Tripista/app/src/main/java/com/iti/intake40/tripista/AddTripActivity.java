package com.iti.intake40.tripista;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class AddTripActivity extends AppCompatActivity{
    private TextView info;
    private TextView text;
    private Button addTripBtn;
    private FireBaseCore core;
    private String name;
    private Button timeBtn;
    private Button dateBtn;
    public Trip tripModel;
    DatePickerDialog datePicker;
    DatePickerDialog datePicker2;
    TimePickerDialog timePicker;
    TimePickerDialog timePicker2;

    private int mYear, mMonth, mDay, hour, min, sec;
    private int mYear2, mMonth2, mDay2, hour2, minute2, sec2;

    Calendar cal;
    Calendar cal2;
    Calendar now;
    Calendar current;
    final static int RQS_1 = 1;
    public int RQS ;
    String TAG = "place";
    public String startPlace;
    public String endPlace;
    public String backStartPlace;
    public String backEndPlace;
    public String time;
    public String date;
    public String strDate;
    public String strTime;
    public String backStrDate;
    public String backStrTime;
    String[] routes;
    public Spinner mSpinner;
    private ArrayAdapter mAdapter;
    Button backDateBtn;
    Button backTimeBtn;
    AutocompleteSupportFragment startAutoCompleteFragment;
    AutocompleteSupportFragment endAutoCompleteFragment;
    String flag;



    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
//        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON|
//                WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD|
//                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED|
//                WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);
        tripModel = new Trip();
        cal = Calendar.getInstance();
        cal2 = Calendar.getInstance();
        now = Calendar.getInstance();
        current = Calendar.getInstance();

        setViews();
        setmSpinner();
        getPlaces();

    }

    private void setAlarm(Calendar targetCal) {
        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        final int id = (int) System.currentTimeMillis();
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), id, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);


    }

    public void tripDate() {
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay = cal.get(Calendar.DAY_OF_MONTH);
        datePicker = new DatePickerDialog(AddTripActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        cal.set(year, monthOfYear, dayOfMonth);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");//                        Date strDate2 = ;
                        strDate = dateFormat.format(cal.getTime());

                    }
                }, mYear, mMonth, mDay);
        //disable past date
        datePicker.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker.show();


    }

    public void tripTime() {
        hour = cal.get(Calendar.HOUR_OF_DAY);
        min = cal.get(Calendar.MINUTE);
        sec = cal.get(Calendar.SECOND);
        sec = 0;
        timePicker = new TimePickerDialog(AddTripActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);
                        cal.set(Calendar.SECOND, 0);
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm:00");
                        strTime = dateFormat.format(cal.getTime());

                    }
                }, hour, min, false);
        timePicker.show();

    }

    public void setViews(){
    setContentView(R.layout.activity_add_trip);
    addTripBtn = findViewById(R.id.addTrip);
    text = findViewById(R.id.Name);
        info = findViewById(R.id.info);
    dateBtn = findViewById(R.id.dateBtn);
    backDateBtn = findViewById(R.id.backDate);
    backTimeBtn = findViewById(R.id.backTime);
    startAutoCompleteFragment = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.startfragment);

    endAutoCompleteFragment = (AutocompleteSupportFragment)
            getSupportFragmentManager().findFragmentById(R.id.endfragment);
    backDateBtn.setVisibility(View.GONE);
    backTimeBtn.setVisibility(View.GONE);


}

    public void setmSpinner(){
    Spinner s1 = (Spinner) findViewById(R.id.routeSpinner);
    routes = getResources().getStringArray(R.array.routes_array);

    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
            android.R.layout.select_dialog_multichoice, routes);

    s1.setAdapter(adapter);
    s1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
            String text = arg0.getSelectedItem().toString();
            if (text.equalsIgnoreCase("round trip")) {
                flag = "round";
                backDateBtn.setVisibility(View.VISIBLE);
                backTimeBtn.setVisibility(View.VISIBLE);
            } else {
                flag = "oneWay";
                backDateBtn.setVisibility(View.GONE);
                backTimeBtn.setVisibility(View.GONE);
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> arg0) {
        }
    });

}

    public void getPlaces(){
    // Initialize Places.
    String apiKey = "AIzaSyDIHrWWuzN2st31DRm6G9KnULCEKSpcV-A";
    Places.initialize(getApplicationContext(), apiKey);
    // Create a new Places client instance.
    PlacesClient placesClient = Places.createClient(this);

    if (!Places.isInitialized()) {
        Places.initialize(getApplicationContext(), "YOUR_API_KEY");
    }


//StartAutoComplete
    startAutoCompleteFragment.setPlaceFields(Arrays.asList(com.google.android.libraries.places.api.model.Place.Field.ID.ID, com.google.android.libraries.places.api.model.Place.Field.NAME));
    startAutoCompleteFragment.setCountry("Eg");
    startAutoCompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
        @Override
        public void onPlaceSelected(@NonNull Place place) {
            System.out.println(place.getName());

            Log.i(TAG, "Place: " + place.getName() + ", " + place.getId());
            startPlace = place.getName();
            backEndPlace = startPlace;

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
            backStartPlace = endPlace;

        }

        @Override
        public void onError(@NonNull Status status) {
            Log.i(TAG, "An error occurred: " + status);


        }
    });
}

    public void setBackDate(View view) {
   backTripDate();
           }

    public void setbackTime(View view) {
        backTripTime();

    }

    public void setDate(View view) {
    tripDate();
    }

    public void setTime(View view) {
        tripTime();
    }

    public void addTrip(View view) {
        name = text.getText().toString();
    if(flag == "round") {
    setRoundTrip();
        }else{
   setOneWayTrip();

            }
    }

    public void setOneWayTrip() {
        if (cal.compareTo(current) <= 0 || strDate == null || strTime == null||name == null || startPlace == null || endPlace == null) {
            Toast.makeText(getApplicationContext(),
                    "Invalid Data",
                    Toast.LENGTH_LONG).show();

        } else if (cal.compareTo(current) > 0 && strDate != null && strTime != null && name != null && startPlace != null && endPlace != null) {

          addTripToFirebase();
            core.addTrip(tripModel);

        }
    }

    public void setRoundTrip(){
            if (cal.compareTo(current) <= 0 || cal2.compareTo(current) <= 0 || cal2.compareTo(cal) <= 0 ||name == null || startPlace == null || endPlace == null || strDate == null || strTime == null || backStrDate == null || backStrTime == null || cal2.compareTo(cal) == 0) {
            Toast.makeText(getApplicationContext(),
                    "Invalid Data"
                    ,Toast.LENGTH_LONG).show();

        } else if (cal.compareTo(current) > 0 && cal2.compareTo(current) > 0 && strDate != null  && strTime != null && backStrTime != null && backStrDate != null && cal2.compareTo(cal) > 0 && name != null && startPlace != null && endPlace != null) {

            addTripToFirebase();
            tripModel.setBackDate(backStrDate);
            tripModel.setBackTime(backStrTime);
            tripModel.setBackStartPoint(backStartPlace);
            tripModel.setBackEndPoint(backEndPlace);
            setAlarm(cal2);
                core.addTrip(tripModel);

        }
}

    public void backTripTime() {
        hour2 = cal2.get(Calendar.HOUR_OF_DAY);
        minute2 = cal2.get(Calendar.MINUTE);
        sec2 = cal2.get(Calendar.SECOND);
        sec2 = 0;
        timePicker2 = new TimePickerDialog(AddTripActivity.this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        cal2.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal2.set(Calendar.MINUTE, minute);
                        cal2.set(Calendar.SECOND, 0);
                        DateFormat dateFormat = new SimpleDateFormat("hh:mm:00");
                        backStrTime = dateFormat.format(cal2.getTime());

                    }
                }, hour2, minute2, false);
        timePicker2.show();
    }

    public void backTripDate() {
        mYear2 = cal2.get(Calendar.YEAR);
        mMonth2 = cal2.get(Calendar.MONTH);
        mDay2 = cal2.get(Calendar.DAY_OF_MONTH);
        datePicker2 = new DatePickerDialog(AddTripActivity.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
//                        cal2.set(year, month, dayOfMonth);
                        cal2.set(Calendar.YEAR, year);
                        cal2.set(Calendar.MONTH, month);
                        cal2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
                        backStrDate = dateFormat.format(cal2.getTime());
                        info.setText(dayOfMonth + "-" + (month + 1) + "-" + year);


                    }
                }, mYear2, mMonth2, mDay2);
        //disable past date
        datePicker2.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePicker2.show();


    }

    public void addTripToFirebase(){
     core = FireBaseCore.getInstance();
     tripModel.setTitle(name);
     tripModel.setStartPoint(startPlace);
     tripModel.setEndPoint(endPlace);
     tripModel.setDate(strDate);
     tripModel.setTime(strTime);

     Toast.makeText(getApplicationContext(),
             "valid Date/Time",
             Toast.LENGTH_LONG).show();
     setAlarm(cal);

       }



}