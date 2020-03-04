package com.iti.intake40.tripista.features.auth.signin;


import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;


public class SigninPresenter implements PresenterInterface {

    private static final String TAG = "signinpresenter";
    private FireBaseCore core;
    private ViewInterface login;
    private FragmentActivity signinActivity;

    public SigninPresenter(PasswordFragment passwordFragment, FireBaseCore core) {
        login = passwordFragment;
        this.core = core;
    }

    public SigninPresenter(FragmentActivity signinActivity) {
        this.signinActivity =  signinActivity;
    }

    @Override
    public void sentMessage(int message) {
        login.sentMessage(message);
    }

    @Override
    public void sentError(int message) {
        login.sentError(message);
    }

    @Override
    public void changeFragment() {
        login.changeFragment();
    }

    @Override
    public void signIn(String email, String password) {
        core.signInWithEmailAndPassword(email, password, this);
    }

    public void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken: " + token);
        final FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());

        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(signinActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            login.sentError(R.string.signin_failed);


                        }
                    }
                });
    }
}
