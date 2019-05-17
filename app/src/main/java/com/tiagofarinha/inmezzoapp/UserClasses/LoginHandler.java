package com.tiagofarinha.inmezzoapp.UserClasses;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.tiagofarinha.inmezzoapp.Fragments.LoginLogic;

public class LoginHandler extends Thread {

    private String email, password;
    private LoginLogic ll;

    public LoginHandler(String email, String password, LoginLogic ll) {
        this.email = email;
        this.password = password;
        this.ll = ll;
    }


    @Override
    public void run() {
        FirebaseAuth auth = FirebaseAuth.getInstance();

        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    ll.checkLoginSucess(true, "");
                } else
                    ll.checkLoginSucess(false, task.getException().getMessage());
            }
        });
    }


}
