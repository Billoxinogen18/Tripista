package com.iti.intake40.tripista.features.auth.home;

import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.UserModel;

public class HomePresenter implements HomeContract.PresenterInterface {
    FireBaseCore core;
    private HomeContract.ViewInterface home;

    public HomePresenter(FireBaseCore core, HomeActivity home) {
        this.core = core;
        this.home = home;
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

    @Override
    public void setUserInfo(UserModel model) {
        home.showUserInfo(model);
    }

    @Override
    public void fetchUserInFo() {
        core.getUserInfo(this);
    }

    @Override
    public void fetchUserInfoByPhone(String number) {
        core.getUserInfoByPhone(this,number);
    }

}
