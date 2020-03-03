package com.iti.intake40.tripista.features.auth.signin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;

public class SigninActivity extends AppCompatActivity implements ViewInterface {
    TextInputEditText etEmailPhone;
    String inputData;
    FireBaseCore core;
    PresenterInterface presenterInterface;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        core = FireBaseCore.getInstance();
        etEmailPhone = findViewById(R.id.et_user_email_phone);

        /*remon

         */

    }

    private boolean isEmailValid(String emailAddress) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return emailAddress.matches(regex);
    }

    public static boolean isPhoneValid(String phone) {
        if (phone.length() != 13 )
            return false;
        String regex = "^\\+?[0-9. ()-]{9,25}$";
        return phone.matches(regex);
    }

    public void gotoPasswordFragment(View view) {
        inputData = etEmailPhone.getText().toString();
        if(!TextUtils.isEmpty(inputData))
        {
            if(isEmailValid(inputData))
            {
                Toast.makeText(this,"email",Toast.LENGTH_LONG).show();
            }
            else if (isPhoneValid(inputData))
            {
                Toast.makeText(this,"phone",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(this,"this is not phone or email please enter correct data",Toast.LENGTH_LONG).show();

            }
        }
    }

    @Override
    public void sentMessage(int message) {

    }

    @Override
    public void sentError(int message) {

    }

    @Override
    public void changeFragment() {

    }
    @Override
    protected void onStart() {
        super.onStart();
        presenterInterface = new SigninPresenter(this,core);
    }
}
