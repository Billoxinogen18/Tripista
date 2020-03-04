package com.iti.intake40.tripista.features.auth.signin;

import com.facebook.AccessToken;
import com.iti.intake40.tripista.core.UserModel;

public interface SigninContract {
    void sentMessage(int message);

    void sentError(int message);

    void changeFragment();
}

interface PresenterInterface extends SigninContract {


    void signIn(String email, String password);

    public void handleFacebookSignin(AccessToken accessToken);

}

interface ViewInterface extends SigninContract {

}
