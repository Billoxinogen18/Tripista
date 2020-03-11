package com.iti.intake40.tripista.features.auth.splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.home.HomeActivity;
import com.iti.intake40.tripista.features.auth.signin.SigninActivity;

public class SplashScreen extends AppCompatActivity implements SplashContract.ViewInterfce {
    private FireBaseCore core;
    private SplashPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        core = FireBaseCore.getInstance();
        presenter = new SplashPresenter(core, this);
        Thread timer = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    presenter.checkCurrentUSer();
                    finish();
                }

            }
        };
        timer.start();
    }

    @Override
    public void goToHome() {
        startActivity(new Intent(this, HomeActivity.class));
        finish();
    }

    @Override
    public void goToSignIn() {
        startActivity(new Intent(this, SigninActivity.class));
        fileList();
    }
}
