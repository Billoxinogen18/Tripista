package com.iti.intake40.tripista.core;


import android.net.Uri;
import android.util.Log;


import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.features.auth.signin.SigninContract;
import com.iti.intake40.tripista.features.auth.signup.SignupContract;
import com.iti.intake40.tripista.features.auth.signup.SignupPresenter;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FireBaseCore {
    private DatabaseReference rootDB;
    private StorageReference rootStorage;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private FirebaseAuth auth;
    private SignupPresenter signinPresenter;
    private  SignupPresenter signupPresenter;
    private String id;
    //make singletone class
    public static FireBaseCore core;

    private FireBaseCore() {
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        rootDB = database.getInstance().getReference().child("tripista");
        rootStorage = FirebaseStorage.getInstance().getReference().child("tripista");
    }

    public static FireBaseCore getInstance() {
        if (core == null) {
            core = new FireBaseCore();

        }
        return core;
    }
    //**************************
    //shrouq

    //**************************
    //mahmoud
    public void addUserData(UserModel model) {
        DatabaseReference profilePath = rootDB.child("users").child("profile").child(id);
        profilePath.setValue(model);
        profilePath = rootDB.child("users").child("profile").child(model.getPhone());
        profilePath.setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signupPresenter.sentMessage(R.string.saved_in_fire_base);
                    signupPresenter.changeActivity();

                }
            }
        });
    }

    public void addUserWithImage(final UserModel model) {
        final StorageReference storagePath = rootStorage.child("Profile").child(id);
        Uri imageUri = Uri.parse(model.getImageUrl());
        StorageTask uploadTask = storagePath.putFile(imageUri);
        uploadTask.continueWithTask(new Continuation() {
            @Override
            public Object then(@NonNull Task task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                return storagePath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri imageUploaded = task.getResult();
                    String imageLink = imageUploaded.toString();
                    model.setImageUrl(imageLink);
                    DatabaseReference profilePath = rootDB.child("users").child("profile").child(id);
                    profilePath.setValue(model);
                    profilePath = rootDB.child("users").child("profile").child(model.getPhone());
                    profilePath.setValue(id);
                    signupPresenter.changeActivity();
                }
            }
        });
    }

    //signup with email and password
    public void signUpEithEmailAndPassword(final UserModel model, final SignupPresenter signupPresenter) {
        this.signupPresenter = signupPresenter;
        auth.createUserWithEmailAndPassword(model.getEmail(), model.getPassWord())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVarificationLink(model);
                        } else {
                            Log.w(TAG, task.getException());
                            signupPresenter.sentError(R.string.error_on_send_verify);
                        }
                    }
                });
    }

    public void sendEmailVarificationLink(final UserModel model) {
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signupPresenter.sentMessage(R.string.signup_email_linke_sent);
                    currentUser = auth.getCurrentUser();
                    id = currentUser.getUid();
                    model.setId(id);
                    if (model.getImageUrl() != null)
                        addUserWithImage(model);
                    else
                        addUserData(model);
                } else {
                    signupPresenter.sentError(R.string.signup_email_linke_not_sent);
                }
            }
        });
    }

    private void signInWithEmailAndPassword(String emailAddress, String password) {

        auth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = auth.getCurrentUser();
                    id = currentUser.getUid();
                    signinPresenter.sentMessage(R.string.logged_in_successfuly);

                } else {
                    signinPresenter.sentError(R.string.login_failed);
                }
            }
        });


    }

}
