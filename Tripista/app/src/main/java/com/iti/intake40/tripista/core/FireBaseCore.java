package com.iti.intake40.tripista.core;


import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.iti.intake40.tripista.R;
import com.iti.intake40.tripista.features.auth.home.HomeContract;
import com.iti.intake40.tripista.features.auth.signin.SigninContract;
import com.iti.intake40.tripista.features.auth.signup.SignupContract;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class FireBaseCore {
    private DatabaseReference rootDB;
    private StorageReference rootStorage;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    private StorageReference storagePath;
    private FirebaseAuth auth;
    private AuthCredential credential;
    private DatabaseReference profilePath;
    private SigninContract.PresenterInterface signinPresenter;
    private SignupContract.PresenterInterface signupPresenter;
    private HomeContract.PresenterInterface homePresenter;
    private String id;
    private String verificationId;
    private DataSnapshot dataSnapshot;
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

    public void addUserData(UserModel model) {
        profilePath = rootDB.child("users").child("profile").child(id);
        profilePath.setValue(model);
        profilePath = rootDB.child("users").child("profile").child(model.getPhone());
        profilePath.setValue(id).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signupPresenter.replyByMessage(R.string.saved_in_fire_base);
                    signupPresenter.replayByChangeActivity();
                }
            }
        });
    }

    public void addUserWithImage(final UserModel model) {
        storagePath = rootStorage.child("Profile").child(id);
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
                    profilePath = rootDB.child("users").child("profile").child(id);
                    profilePath.setValue(model);
                    profilePath = rootDB.child("users").child("profile").child(model.getPhone());
                    profilePath.setValue(id);
                    signupPresenter.replayByChangeActivity();
                }
            }
        });
    }

    //signup with email and password
    public void signUpEithEmailAndPassword(final UserModel model, SignupContract.PresenterInterface presenter) {
        this.signupPresenter = presenter;
        auth.createUserWithEmailAndPassword(model.getEmail(), model.getPassWord())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            sendEmailVarificationLink(model);
                        } else {
                            Log.w(TAG, task.getException());
                            signupPresenter.replyByError(R.string.error_on_send_verify);
                        }
                    }
                });
    }

    public void sendEmailVarificationLink(final UserModel model) {
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    signupPresenter.replyByMessage(R.string.signup_email_linke_sent);
                    currentUser = auth.getCurrentUser();
                    id = currentUser.getUid();
                    model.setId(id);
                    if (model.getImageUrl() != null)
                        addUserWithImage(model);
                    else
                        addUserData(model);
                } else {
                    signupPresenter.replyByError(R.string.signup_email_linke_not_sent);
                }
            }
        });
    }

    public void signInWithEmailAndPassword(String emailAddress, String password, SigninContract.PresenterInterface presenter) {
        signinPresenter = presenter;
        auth.signInWithEmailAndPassword(emailAddress, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    currentUser = auth.getCurrentUser();
                    id = currentUser.getUid();
                    if (isCheckedEmailVerfication()) {

                        signinPresenter.replyByMessage(R.string.logged_in_successfuly);
                        signinPresenter.replayByChangeFragment();
                    } else {
                        signinPresenter.replyByError(R.string.verfy_error);
                    }

                } else {
                    signinPresenter.replyByError(R.string.login_failed);
                }
            }
        });


    }

    private boolean isCheckedEmailVerfication() {
        return auth.getCurrentUser().isEmailVerified();
    }

    public void handleFacebookAccessToken(AccessToken token, FragmentActivity signinActivity, SigninContract.PresenterInterface presenter) {
        signinPresenter = presenter;
        Log.d(TAG, "handleFacebookAccessToken: " + token);
        auth = FirebaseAuth.getInstance();
        credential = FacebookAuthProvider.getCredential(token.getToken());

        auth.signInWithCredential(credential)
                .addOnCompleteListener(signinActivity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            currentUser = auth.getCurrentUser();
                            UserModel model = new UserModel(currentUser.getUid(), currentUser.getDisplayName(), currentUser.getPhotoUrl().toString(), currentUser.getEmail());
                            checkUser(model);
                            signinPresenter.replayByChangeFragment();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            signinPresenter.replyByError(R.string.signin_failed);
                        }
                    }
                });
    }

    //check facebook user id exist before or not
    private void checkUser(final UserModel model) {
        currentUser = auth.getCurrentUser();
        id = currentUser.getUid();
        profilePath = rootDB.child("users").child("profile").child(id);
        profilePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() == null) {
                    addFacebookUser(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    //add user login by face book
    private void addFacebookUser(final UserModel model) {
                    profilePath = rootDB.child("users").child("profile").child(id);
                    profilePath.setValue(model);
                }

    public void signOut() {
        auth.signOut();
    }
    // get info for user
    public void getUserInfo( HomeContract.PresenterInterface home)
    {
        homePresenter =home ;
        currentUser = auth.getCurrentUser();
        id = currentUser.getUid();
        profilePath = rootDB.child("users").child("profile").child(id);
        profilePath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue() != null) {
                    UserModel model =dataSnapshot.getValue(UserModel.class);
                    homePresenter.setUserInfo(model);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
