package com.iti.intake40.tripista.features.auth.signin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseUser;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.features.auth.Delegate;

import static com.iti.intake40.tripista.features.auth.signin.SigninActivity.PHONE_ARG;

public class PhoneVerficiation extends Fragment implements SigninContract.ViewInterface {
    private TextInputEditText etPhoneCode;
    private FloatingActionButton nextBtn;
    private String phone;
    private FireBaseCore core;
    private SigninContract.PresenterInterface presenterInterface;
    private Delegate delegate;


    public PhoneVerficiation() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        phone= getArguments().getString(PHONE_ARG);
        core=FireBaseCore.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_phone_verficiation, container, false);
        etPhoneCode = view.findViewById(R.id.et_phone_verfy_code);
        nextBtn = view.findViewById(R.id.sign_in_phone);
        delegate = (Delegate)getActivity();
        delegate.sendVerificationCode(phone);

        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeFragment(null);
            }
        });
        return view;
    }

    @Override
    public void sentMessage(int message) {
        Toast.makeText(getActivity(),getResources().getString(message),Toast.LENGTH_LONG).show();

    }

    @Override
    public void sentError(int message) {
        Toast.makeText(getActivity(),getResources().getString(message),Toast.LENGTH_LONG).show();

    }

    @Override
    public void changeFragment(FirebaseUser user) {
    //go to home
        Toast.makeText(getActivity(),"go to home",Toast.LENGTH_LONG).show();
    }
}
