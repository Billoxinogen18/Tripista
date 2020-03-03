package com.iti.intake40.tripista.features.auth.signin;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.UserModel;

public class SigninPresenter implements  PresenterInterface{
    private FireBaseCore core;
    private ViewInterface signinActivity;
    public SigninPresenter(SigninActivity signinActivity, FireBaseCore core) {
        this.signinActivity = signinActivity;
        this.core = core;
    }

    @Override
    public void sentMessage(int message) {

    }

    @Override
    public void sentError(int message) {

    }

    @Override
    public void changeFragment() {

    }

    @Override
    public void signIn(UserModel model) {

    }

     /*
    mobile no auth
     */

    /*
    Facebook auth
     */
}
