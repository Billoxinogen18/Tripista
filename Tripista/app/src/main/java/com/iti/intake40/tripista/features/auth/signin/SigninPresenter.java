package com.iti.intake40.tripista.features.auth.signin;


import com.iti.intake40.tripista.core.FireBaseCore;


public class SigninPresenter implements  PresenterInterface{
    private FireBaseCore core;
    private ViewInterface login;
    public SigninPresenter(PasswordFragment passwordFragment, FireBaseCore core) {
        login = passwordFragment;
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
        core.signInWithEmailAndPassword(email,password,this);
    }


}
