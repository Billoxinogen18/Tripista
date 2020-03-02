package com.iti.intake40.tripista.core;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

public class FireBaseCore {
    private DatabaseReference rootDB;
    private StorageReference rootStorage;
    private FirebaseUser currentUser;
    private FirebaseDatabase database;
    //make singletone class
    public static FireBaseCore core;

    private FireBaseCore() {
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
    public void addUserData(UserModel model, final Context context) {
        DatabaseReference profilePath = rootDB.child("users").child("profile").child("123");
        profilePath.setValue(model).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(context, "successfully saved", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void addUserWithImage(final UserModel model, final Context context,Uri imageUri) {
        final StorageReference storagePath = rootStorage.child("Profile").child("123 .jpg");
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
                    DatabaseReference profilePath = rootDB.child("users").child("profile").child("123");
                    profilePath.setValue(model);
                }
            }
        });
    }
}
