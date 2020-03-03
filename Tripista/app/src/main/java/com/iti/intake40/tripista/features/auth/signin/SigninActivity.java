package com.iti.intake40.tripista.features.auth.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;

public class SigninActivity extends AppCompatActivity  {
    FragmentManager mgr;
    Fragment signIn;
    FragmentTransaction trns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mgr = getSupportFragmentManager();
        trns = mgr.beginTransaction();
        signIn = new SignInFragment();
        trns.replace(R.id.container, signIn, "signFragment");
        trns.commit();

        /*remon

         */

    }




}
