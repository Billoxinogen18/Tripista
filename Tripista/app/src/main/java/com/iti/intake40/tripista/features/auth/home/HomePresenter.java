package com.iti.intake40.tripista.features.auth.home;

import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.core.FireBaseCore;

public class HomePresenter implements HomeContract.PresenterInterface {
    FireBaseCore core;
    public HomePresenter(FireBaseCore core) {
        this.core=core;
    }

    @Override
    public void replyByMessage(int message) {

    }

    @Override
    public void replyByError(int message) {

    }

    @Override
    public void replayByChangeFragment(FirebaseUser user) {

    }

    @Override
    public void replayChangeActivity() {

    }

    @Override
    public void signOut() {
     core.signOut();
    }
}
