package com.tiagofarinha.inmezzoapp.UserClasses;

import android.os.AsyncTask;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Configurations.ProfileConfig;

public class ConfigHandler extends AsyncTask {

    private static final int TOTAL_OPERATIONS = 3;

    private String name, email, bio;

    private String messages;
    private int operations;

    private ProfileConfig cl;

    public ConfigHandler(ProfileConfig cl, String name, String email, String bio) {
        this.cl = cl;
        this.name = name;
        this.email = email;
        this.bio = bio;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        operations = TOTAL_OPERATIONS;
        messages = "";

        cl.getLoadingBar().setVisibility(View.VISIBLE);
        updateEverything();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);

        cl.getLoadingBar().setVisibility(View.GONE);
        cl.showResults(messages);
    }

    @Override
    protected synchronized Object doInBackground(Object[] objects) {
        while (operations != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private void updateEverything() {
        updateName();
        updateEmail();
        updateBios();
    }

    private synchronized void taskOver(String message, int mode) {
        if (mode == 1) {
            messages += " - " + message;
            if (operations != 1)
                messages += "\n";
        }
        operations--;
        notify();
    }

    private void updateEmail() {
        if (!email.equals(cl.getCurrEmail())) {
            cl.getUser().updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    cl.getUser().sendEmailVerification();
                    taskOver("Email Atualizado. Verifique o seu Email;", 1);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    taskOver("Por motivos de segurança entre novamente na conta e mude o email.", 1);
                }
            });
        } else taskOver("", 0);
    }

    private void updateName() {
        if (!name.equals(cl.getCurrName())) {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().setDisplayName(name).build();
            cl.getUser().updateProfile(profileUpdates);

            FirebaseDatabase.getInstance().getReference().child("users").child(cl.getUser().getUid()).child("user_name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    taskOver("Nome Atualizado", 1);
                }
            });
        } else
            taskOver("", 0);
    }

    private void updateBios() {
        if (!bio.equals(cl.getCurrBio())) {
            FirebaseDatabase.getInstance().getReference().child("users").child(cl.getUser().getUid()).child("user_bio").setValue(bio).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    taskOver("Biografia Atualizada", 1);
                }
            });
        } else taskOver("", 0);
    }


}
