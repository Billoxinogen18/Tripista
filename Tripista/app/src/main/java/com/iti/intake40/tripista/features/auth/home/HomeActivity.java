package com.iti.intake40.tripista.features.auth.home;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.R;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Intent siginIntent = getIntent();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        Log.d(TAG, "onCreate: "+ user.toString());
        Log.d(TAG, "onCreate: " + user.getDisplayName());

    }
}
