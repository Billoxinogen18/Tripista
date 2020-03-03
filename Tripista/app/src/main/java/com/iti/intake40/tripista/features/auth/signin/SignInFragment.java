package com.iti.intake40.tripista.features.auth.signin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.Delegate;


public class SignInFragment extends Fragment  {
    FloatingActionButton nextBtn;
    TextInputEditText etEmailPhone;
    String inputData;
    Delegate delegate;
    public SignInFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
        etEmailPhone =view.findViewById(R.id.et_user_email_phone);
        nextBtn = view.findViewById(R.id.next_button);
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPasswordFragment();
            }
        });
        return view;
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

    public void gotoPasswordFragment() {
        inputData = etEmailPhone.getText().toString();
        if(!TextUtils.isEmpty(inputData))
        {
            if(isEmailValid(inputData))
            {
                delegate =(Delegate) getActivity();
                Toast.makeText(getActivity(),"email",Toast.LENGTH_LONG).show();
                delegate.setData(inputData);

            }
            else if (isPhoneValid(inputData))
            {
                Toast.makeText(getActivity(),"phone",Toast.LENGTH_LONG).show();
            }
            else
            {
                Toast.makeText(getActivity(),"this is not phone or email please enter correct data",Toast.LENGTH_LONG).show();

            }
        }
    }


}
