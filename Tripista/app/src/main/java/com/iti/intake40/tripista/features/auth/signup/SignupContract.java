package com.iti.intake40.tripista.features.auth.signup;

import com.iti.intake40.tripista.core.UserModel;

public interface SignupContract {
    void sentMessage(int message);

    void sentError(int message);

    void changeActivity();
}
interface PresenterInterface extends SignupContract{

    void signup(UserModel model);
}

interface ViewInterface extends SignupContract {

}
