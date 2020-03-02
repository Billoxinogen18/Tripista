package com.iti.intake40.tripista.features.auth.signup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.core.FireBaseCore;
import com.iti.intake40.tripista.core.UserModel;
import com.theartofdev.edmodo.cropper.CropImage;

public class SignUp extends AppCompatActivity {
    private Uri imageUri;
    private TextInputEditText etUserName;
    private TextInputEditText etPasword;
    private TextInputEditText etRePassword;
    private TextInputEditText etPhoneNumber;
    ImageView profileImage;
    String userName;
    String phoneNumber;
    String password;
    String repassword;
    UserModel model;
    FireBaseCore core;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        profileImage = findViewById(R.id.profile_image);
        etUserName = findViewById(R.id.et_user_name);
        etPasword = findViewById(R.id.et_password);
        etRePassword = findViewById(R.id.et_confirm_password);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        core = FireBaseCore.getInstance();
        model = new UserModel();
    }

    //save data on fire base
    public void saveProfileData(View view) {
        userName = etUserName.getText().toString();
        phoneNumber = etPhoneNumber.getText().toString();
        password = etPasword.getText().toString();
        repassword = etRePassword.getText().toString();
        if (!TextUtils.isEmpty(userName) && !TextUtils.isEmpty(phoneNumber) && !TextUtils.isEmpty(password) && !TextUtils.isEmpty(repassword)) {
            if (imageUri != null) {
               model.setName(userName);
               model.setPhone(phoneNumber);
               model.setPassWord(password);
               core.addUserWithImage(model,this,imageUri);
            } else {
                core.addUserData(model,this);
            }
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
}
