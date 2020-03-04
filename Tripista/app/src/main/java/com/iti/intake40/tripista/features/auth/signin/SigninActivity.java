package com.iti.intake40.tripista.features.auth.signin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.Delegate;

public class SigninActivity extends AppCompatActivity implements Delegate {
    public final static String EMAIL_ARG="Email";
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


    @Override
    public void setData(String data) {
        Bundle bundle = new Bundle();
        bundle.putString(EMAIL_ARG,data);
       // set Fragmentclass Arguments
        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setArguments(bundle);
        trns = mgr.beginTransaction();
        trns.replace(R.id.container, passwordFragment, "passwordFragment");
        trns.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //android.app.Fragment fragment = getFragmentManager().findFragmentByTag("signFragment");
        Fragment fragment = mgr.findFragmentByTag("signFragment");
        fragment.onActivityResult(requestCode, resultCode, data);
    }
}
