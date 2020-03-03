package com.iti.intake40.tripista.features.auth.signin;

import com.iti.intake40.tripista.core.UserModel;

public interface SigninContract {
    void sentMessage(int message);

    void sentError(int message);

    void changeFragment();
}
interface PresenterInterface  extends  SigninContract{


    void signIn(String email,String password);

}

interface ViewInterface extends SigninContract{

}
