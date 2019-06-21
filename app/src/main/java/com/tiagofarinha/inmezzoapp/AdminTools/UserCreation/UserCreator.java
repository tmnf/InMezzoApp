package com.tiagofarinha.inmezzoapp.AdminTools.UserCreation;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
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
            createClient();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Creates User in Database
    private void createClient() {
        final FirebaseAuth auth = FirebaseAuth.getInstance();

        String pic_url = phone + ".jpg";

        final User us = new User(name, birth, voice, Integer.parseInt(phone), pic_url, Integer.parseInt(mode));

        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                createObject(us, task.getResult().getUser());
            }
        });

    }

    // Associates Auth User To Model User
    private void createObject(User us, FirebaseUser user) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
        user.updateProfile(profileUpdates);

        FirebaseDatabase myRef = FirebaseDatabase.getInstance();
        myRef.getReference().child("users").child(user.getUid()).setValue(us);

        FirebaseAuth.getInstance().sendPasswordResetEmail(email);

        //AdminLogic.getInstance().onSucess();

        Log.d("USERS", name + " Adicionado");
        interrupt();
    }

}
