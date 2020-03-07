package com.iti.intake40.tripista.features.auth.signin;

import com.facebook.AccessToken;

public interface SigninContract {
    interface PresenterInterface {
        void replyByMessage(int message);

        void replyByError(int message);

        void replayByChangeFragment();

        void signIn(String email, String password);

        void handleFacebookSignin(AccessToken accessToken);

        void signInWithMobile(String phone);

    }

    interface ViewInterface {
        void sentMessage(int message);

        void sentError(int message);

        void changeFragment();
    }
}


