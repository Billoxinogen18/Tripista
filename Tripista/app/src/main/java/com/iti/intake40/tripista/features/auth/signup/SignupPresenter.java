package com.iti.intake40.tripista.features.auth.signup;


import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.UserModel;

public class SignupPresenter implements PresenterInterface {
    private FireBaseCore core;
    private ViewInterface signUp;
    public SignupPresenter(SignUp signUp, FireBaseCore core) {
        this.core = core;
        this.signUp = signUp;
    }

    @Override
    public void signup(UserModel model) {
        core.signUpEithEmailAndPassword(model, this);

    }

    @Override
    public void sentMessage(int messegeResourse) {
        signUp.sentMessage(messegeResourse);
    }

    @Override
    public void sentError(int messegeResourse) {
        signUp.sentError(messegeResourse);

    }

    @Override
    public void changeActivity() {
        signUp.changeActivity();
    }
}
