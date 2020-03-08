package com.iti.intake40.tripista.features.auth.signup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.model.UserModel;
import com.iti.intake40.tripista.features.auth.signin.SigninActivity;
import com.theartofdev.edmodo.cropper.CropImage;

public class SignUp extends AppCompatActivity implements SignupContract.ViewInterface {
    ImageView profileImage;
    String userName;
    String phoneNumber;
    String password;
    String repassword;
    String email;
    UserModel model;
    FireBaseCore core;
    private Uri imageUri;
    private TextInputEditText etUserName;
    private TextInputEditText etPasword;
    private TextInputEditText etRePassword;
    private TextInputEditText etPhoneNumber;
    private TextInputEditText etEmail;
    private SignupContract.PresenterInterface presenterInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        profileImage = findViewById(R.id.profile_image);
        etUserName = findViewById(R.id.et_user_name);
        etPasword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_confirm_password);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etEmail = findViewById(R.id.et_user_email);
        core = FireBaseCore.getInstance();
        model = new UserModel();
    }

    //save data on fire base
    public void saveProfileData(View view) {
        userName = etUserName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        password = etPasword.getText().toString();
        repassword = etRePassword.getText().toString();
        email = etEmail.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
            model.setName(userName);
            model.setPhone(phoneNumber);
            model.setPassWord(password);
            if (imageUri != null)
                model.setImageUrl(imageUri.toString());
            model.setEmail(email);
            presenterInterface.signup(model);
        }
    }

    //get image from galary
    public void getImgFromGalory(View view) {
        CropImage.activity(imageUri)
                .setAspectRatio(1, 1)
                .start(SignUp.this);
    }

    //get croped image as result by activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageUri = result.getUri();
            profileImage.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    public void sentMessage(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_LONG).show();

    }

    @Override
    public void sentError(int message) {
        Toast.makeText(this, getResources().getString(message), Toast.LENGTH_LONG).show();
    }

    @Override
    public void changeActivity() {
        Intent goSignIn = new Intent(this, SigninActivity.class);
        goSignIn.putExtra("email", email);
        goSignIn.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(goSignIn);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenterInterface = new SignupPresenter(this, core);
    }


}
