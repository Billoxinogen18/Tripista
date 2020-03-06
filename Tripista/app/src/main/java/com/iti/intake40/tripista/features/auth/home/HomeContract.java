package com.iti.intake40.tripista.features.auth.home;

import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseUser;

public interface HomeContract {
    interface PresenterInterface {
        void replyByMessage(int message);
        void replyByError(int message);
        void replayByChangeFragment(FirebaseUser user);
        void replayChangeActivity();
        void signOut();
    }

    interface ViewInterface  {
        void sentMessage(int message);
        void sentError(int message);
        void changeActivity();
    }
}


