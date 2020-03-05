package com.iti.intake40.tripista.features.auth.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.facebook.Profile;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.R;

import java.net.URL;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "Home";
    private Toolbar toolbar;
    private NavigationView navigationView;
    private View header;
    private DrawerLayout drawerLayout;
    private ProfilePictureView profilePictureView;
    private TextView userNameTextView;
    private TextView emailTextView;
    private URL img_value = null;
    private FirebaseUser firebaseUser;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        navigationView = findViewById(R.id.nav_view);
        header = navigationView.getHeaderView(0);
        drawerLayout = findViewById(R.id.drawer_layout);
        //imageView = header.findViewById(R.id.nav_header_image);
        profilePictureView = header.findViewById(R.id.friendProfilePicture);
        userNameTextView = header.findViewById(R.id.nav_header_userName);
        emailTextView = header.findViewById(R.id.nav_header_email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //handle toggle button click
        drawerLayout.addDrawerListener(toggle);
        //add animation to toggle button
        toggle.syncState();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userNameTextView.setText(firebaseUser.getDisplayName());
        emailTextView.setText(firebaseUser.getEmail());

        profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
        Log.d(TAG, "onCreate: " + firebaseUser.getPhotoUrl());
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
