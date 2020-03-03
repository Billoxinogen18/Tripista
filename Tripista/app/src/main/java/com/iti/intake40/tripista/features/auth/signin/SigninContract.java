package com.iti.intake40.tripista.features.auth.signin;

import com.iti.intake40.tripista.core.UserModel;

public interface SigninContract {
    void sentMessage(int message);

    void sentError(int message);

    void changeFragment();
}
interface PresenterInterface  extends  SigninContract{
    @Override
    void sentMessage(int message);

    @Override
    void sentError(int message);

    @Override
    void changeFragment();

    void signIn(UserModel model);

}

interface ViewInterface extends SigninContract{
    @Override
    void sentMessage(int message);

    @Override
    void sentError(int message);

    @Override
    void changeFragment();
}
