package com.iti.intake40.tripista.features.auth.signin;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.UserModel;

public class SigninPresenter implements  PresenterInterface{
    private FireBaseCore core;
    private ViewInterface signinFragment;
    public SigninPresenter(SignInFragment signinFragment, FireBaseCore core) {
        this.signinFragment = signinFragment;
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
