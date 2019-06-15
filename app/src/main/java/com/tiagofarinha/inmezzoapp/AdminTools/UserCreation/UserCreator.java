package com.tiagofarinha.inmezzoapp.AdminTools.UserCreation;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.AdminTools.AdminLogic;
import com.tiagofarinha.inmezzoapp.Models.User;

public class UserCreator extends Thread {

    /* Handles User Creation */

    private String email, pass, name, birth, voice, phone, mode;

    public UserCreator(String email, String pass, String name, String birth, String voice, String phone, String mode) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.birth = birth;
        this.voice = voice;
        this.phone = phone;
        this.mode = mode;
    }

    public void run() {
        try {
            createClient(email, pass, name, birth, voice, Integer.parseInt(phone), Integer.parseInt(mode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creates User in Database
    public void createClient(final String email, final String password, final String user_name, String user_birthday, String user_voice, int user_phone, int mode) {
        final FirebaseAuth auth = FirebaseAuth.getInstance();
        final FirebaseDatabase myRef = FirebaseDatabase.getInstance();

        String pic_url = user_phone + ".jpg";

        final User us = new User(user_name, user_birthday, user_voice, user_phone, email, pic_url, mode);

        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                createObject(email, user_name, us, task.getResult().getUser());
            }
        });

    }

    // Associates Auth User To Model User
    private void createObject(String email, String user_name, User us, FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(user_name).build();
        user.updateProfile(profileUpdates);

        FirebaseDatabase myRef = FirebaseDatabase.getInstance();
        myRef.getReference().child("users").child(user.getUid()).setValue(us);

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);

        AdminLogic.getInstance().onSucess();
        interrupt();
    }

}
