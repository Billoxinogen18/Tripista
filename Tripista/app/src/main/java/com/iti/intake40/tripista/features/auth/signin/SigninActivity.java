package com.iti.intake40.tripista.features.auth.signin;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.home.HomeActivity;

import java.util.concurrent.TimeUnit;

public class SigninActivity extends AppCompatActivity implements Delegate {
    public final static String EMAIL_ARG = "Email";
    public final static String PHONE_ARG = "Phone";
    FragmentManager mgr;
    Fragment signIn;
    FragmentTransaction trns;
    String verificationId;
    private FirebaseAuth auth;
    private SigninContract.PresenterInterface presenterInterface;
    private FireBaseCore core;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        /** get the code sent by sms automatically **/
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            //viewInterface.onVerificationFailed(e);

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        core = FireBaseCore.getInstance();
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser currentUser = firebaseAuth.getCurrentUser();
        if (currentUser != null) {
            Intent gotoHomeIntent = new Intent(SigninActivity.this, HomeActivity.class);
            gotoHomeIntent.putExtra("user", currentUser);
            startActivity(gotoHomeIntent);
            finish();
        } else {
            mgr = getSupportFragmentManager();
            trns = mgr.beginTransaction();
            signIn = new SignInFragment();
            trns.replace(R.id.container, signIn, "signFragment");
            trns.commit();
        }
    }

    //send phone
    public void verifyCode(String code) {
        PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(verificationId, code);
        /** sign in method **/
        core.signInWithCredential(phoneAuthCredential, presenterInterface);

    }

    @Override
    public void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );

    }

    @Override
    public void setData(String data) {
        Bundle bundle = new Bundle();
        bundle.putString(EMAIL_ARG, data);
        // set Fragmentclass Arguments
        PasswordFragment passwordFragment = new PasswordFragment();
        passwordFragment.setArguments(bundle);
        trns = mgr.beginTransaction();
        trns.replace(R.id.container, passwordFragment, "passwordFragment");
        trns.commit();
    }

    @Override
    public void changeFragment(String data) {
        Bundle bundle = new Bundle();
        bundle.putString(PHONE_ARG, data);
        PhoneVerficiation phoneVerficiation = new PhoneVerficiation();
        phoneVerficiation.setArguments(bundle);
        trns = mgr.beginTransaction();
        core = FireBaseCore.getInstance();
        presenterInterface = new SigninPresenter(phoneVerficiation, core);
        trns.replace(R.id.container, phoneVerficiation, "phoneFragment");
        trns.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Fragment fragment = mgr.findFragmentByTag("signFragment");
        fragment.onActivityResult(requestCode, resultCode, data);
    }


}
