package com.iti.intake40.tripista.features.auth.signin;


import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
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

    public SigninPresenter(FragmentActivity signinActivity, SignInFragment signInFragment, FireBaseCore core) {
        this.signinActivity = signinActivity;
        login = signInFragment;
        this.core = core;
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

    public void handleFacebookSignin(AccessToken accessToken) {
        core.handleFacebookAccessToken(accessToken, signinActivity);
    }

}
