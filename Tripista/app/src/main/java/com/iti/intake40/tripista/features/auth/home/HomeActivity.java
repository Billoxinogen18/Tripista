package com.iti.intake40.tripista.features.auth.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.widget.ProfilePictureView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.HistoryFragment;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.UpcommingFragment;
import com.iti.intake40.tripista.features.auth.signin.SignInFragment;
import com.iti.intake40.tripista.features.auth.signin.SigninActivity;

import java.net.URL;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Home";
    private Toolbar toolbar;
    private NavigationView navigationView;
    private View header;
    private DrawerLayout drawerLayout;
    private ImageView profilePictureView;
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
        navigationView.setNavigationItemSelectedListener(this);
        header = navigationView.getHeaderView(0);
        drawerLayout = findViewById(R.id.drawer_layout);
        //imageView = header.findViewById(R.id.nav_header_image);
        profilePictureView = header.findViewById(R.id.nav_profile_image);
        userNameTextView = header.findViewById(R.id.nav_header_userName);
        emailTextView = header.findViewById(R.id.nav_header_email);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //handle toggle button click
        drawerLayout.addDrawerListener(toggle);
        //add animation to toggle button
        toggle.syncState();

        if (savedInstanceState == null) {
            //open the first fragment imdedaitely
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new UpcommingFragment()).commit();
            //select the first item
            navigationView.setCheckedItem(R.id.nav_upcomming);
        }

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();

        userNameTextView.setText(firebaseUser.getDisplayName());
        emailTextView.setText(firebaseUser.getEmail());

        //profilePictureView.setProfileId(Profile.getCurrentProfile().getId());
        Glide.with(this)
                .load(firebaseUser.getPhotoUrl().toString() + "?height=500")
                .centerCrop()
                .placeholder(R.drawable.com_facebook_profile_picture_blank_portrait)
                .into(profilePictureView);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_upcomming:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new UpcommingFragment()).commit();
                break;
            case R.id.nav_history:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HistoryFragment()).commit();
                break;

            case R.id.nav_sync:
                Toast.makeText(this, "syncing", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_logout:
                //log the user out
                //go to signin screen
                firebaseAuth.signOut();
                LoginManager.getInstance().logOut();
                Intent signoutIntent = new Intent(this, SigninActivity.class);
                startActivity(signoutIntent);
                finish();
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
