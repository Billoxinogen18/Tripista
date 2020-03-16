package com.iti.intake40.tripista.features.auth.home;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.OnTripsLoaded;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.Trip;
import com.iti.intake40.tripista.core.model.UserModel;

import java.util.ArrayList;
import java.util.List;

public class HomePresenter implements HomeContract.PresenterInterface {
    FireBaseCore core;
    private HomeContract.ViewInterface home;
    private static final String TAG = "HomePresenter";

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
        core.getUserInfoByPhone(this, number);
    }

    @Override
    public List<Trip> getUserTrips() {
        return null;
    }

    //    List<Trip> myTrips = new ArrayList<>();
//
//    public List<Trip> getUserTrips(){
//        core.getTripsForCurrentUser(new OnTripsLoaded() {
//            @Override
//            public void onTripsLoaded(List<Trip> trips) {
//               // myTrips = trips;
//                Log.d(TAG, "onTripsLoaded: " + trips.toString());
//            }
//        return myTrips;
//    }

}
