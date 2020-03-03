package com.iti.intake40.tripista.features.auth.signin;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iti.intake40.tripista.R;

import static com.iti.intake40.tripista.features.auth.signin.SigninActivity.EMAIL_ARG;

public class PasswordFragment extends Fragment {
    String email;
    public PasswordFragment() {
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
        email= getArguments().getString(EMAIL_ARG);
        return inflater.inflate(R.layout.fragment_password, container, false);
    }
}
