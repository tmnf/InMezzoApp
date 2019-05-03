package com.tiagofarinha.inmezzoapp.AdminTools;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Models.User;

public class ClientImporter {

    public static void createClient(final String email, final String password, final String user_name, String user_birthday, String user_voice, int user_phone, int mode){
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase myRef = FirebaseDatabase.getInstance();

        String pic_url = user_phone + ".jpg";

        final User us = new User(user_name,user_birthday,user_voice,user_phone, email, pic_url, mode);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                createObject(email, user_name, us, task.getResult().getUser());
            }
        });

    }

    private static void createObject(String email, String user_name, User us, FirebaseUser user){
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user_name).build();
        user.updateProfile(profileUpdates);

        FirebaseDatabase myRef = FirebaseDatabase.getInstance();
        myRef.getReference().child("users").child(user.getUid()).setValue(us);

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);

        AdminLogic.getInstance().onSucess();
    }
}
