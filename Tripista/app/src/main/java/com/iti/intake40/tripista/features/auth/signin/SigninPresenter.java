package com.iti.intake40.tripista.features.auth.signin;


import android.view.View;

import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.iti.intake40.tripista.core.FireBaseCore;


public class SigninPresenter implements SigninContract.PresenterInterface {

    private static final String TAG = "signinpresenter";
    private FireBaseCore core;
    private SigninContract.ViewInterface login;
    private FragmentActivity signinActivity;

    public SigninPresenter(PasswordFragment passwordFragment, FireBaseCore core) {
        login = passwordFragment;
        this.core = core;
    }

    public SigninPresenter(FragmentActivity signinActivity, SignInFragment signInFragment, FireBaseCore core) {
        this.signinActivity = signinActivity;
        login = signInFragment;
        this.core = core;
    }


    public SigninPresenter(PhoneVerficiation phoneVerficiation, FireBaseCore core) {
        login = phoneVerficiation;
        this.core = core;
    }




    @Override
    public void replyByMessage(int message) {
        login.sentMessage(message);
    }

    @Override
    public void replyByError(int message) {
        login.sentError(message);
    }

    @Override
    public void replayByChangeFragment() {
        login.changeFragment();
    }

    @Override
    public void signIn(String email, String password) {
        core.signInWithEmailAndPassword(email, password, this);
    }

    @Override
    public void handleFacebookSignin(AccessToken accessToken) {
        core.handleFacebookAccessToken(accessToken, signinActivity, this);
    }

    @Override
    public void signInWithMobile(String phone,SigninActivity activity) {
     //   core.sendVerificationCode(phone,this,activity);
    }


}
