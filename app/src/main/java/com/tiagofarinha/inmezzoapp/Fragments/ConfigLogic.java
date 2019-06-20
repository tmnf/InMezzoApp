package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class ConfigLogic extends Fragment {

    private static final int TOTAL_OPERATIONS = 3;

    private String currName, currEmail, currBio;
    private FirebaseUser user;

    private String messages;
    private int operations;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_config, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        currName = user.getDisplayName();
        currEmail = user.getEmail();
        currBio = MainMethods.getInstance().getAuxUser().getUser_bio();

        operations = TOTAL_OPERATIONS;
        messages = "";

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        final EditText name, email, bio;

        name = view.findViewById(R.id.config_nome_field);
        email = view.findViewById(R.id.config_email_field);
        bio = view.findViewById(R.id.config_bio_field);

        name.setText(currName);
        email.setText(currEmail);
        bio.setText(currBio);

        Button resetPassword, save;

        resetPassword = view.findViewById(R.id.config_pass_reset);
        save = view.findViewById(R.id.config_save);


        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveInformation(name, email, bio, user);
            }
        });
    }

    private void resetPassword() {
    }

    private void saveInformation(EditText name, EditText email, EditText bio, final FirebaseUser user) {
        String nameText, emailText, bioText;

        nameText = name.getText().toString();
        emailText = email.getText().toString();
        bioText = bio.getText().toString();

        if (!(nameText.equals(currName) && emailText.equals(currEmail) && bioText.equals(currBio)))
            updateEverything(nameText, emailText, bioText);
        else
            messages += "Nenhuma mudança detectada!";

        showResults();
    }

    public void updateEverything(String name, String email, String bio) {
        updateName(name);
        updateEmail(email);
        updateBios(email);

        waitForEverything();
    }

    private synchronized void waitForEverything() {
        while (operations != 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        showResults();
    }

    private void showResults() {
        Utils.showMessage(messages);
        MenuUtils.filterMenuItem(R.id.menu_perfil);
    }

    private synchronized void taskOver(String message, int mode) {
        if (mode == 1)
            messages += " - " + message + "\n";
        operations--;
        notify();
    }

    private void updateEmail(String email) {
        if (!email.equals(currEmail)) {
            user.updateEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    user.sendEmailVerification();
                    taskOver("Email Atualizado. Verifique o seu Email;", 1);
                }
            });
        } else taskOver("", 0);
    }

    private void updateName(String name) {
        if (!name.equals(currName)) {
            FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("user_name").setValue(name).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    taskOver("Nome Atualizado", 1);
                }
            });
        } else
            taskOver("", 0);
    }

    private void updateBios(String bio) {
        if (!bio.equals(currBio)) {
            FirebaseDatabase.getInstance().getReference().child("users").child(user.getUid()).child("user_bio").setValue(bio).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    taskOver("Biografia Atualizada", 1);
                }
            });
        } else taskOver("", 0);
    }
}
