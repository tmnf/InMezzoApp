package com.tiagofarinha.inmezzoapp.Configurations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.UserClasses.ConfigHandler;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class ProfileConfig extends Fragment {

    private String currName, currEmail, currBio;
    private FirebaseUser user;

    private ProgressBar loadingBar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_config, container, false);

        user = FirebaseAuth.getInstance().getCurrentUser();

        loadingBar = view.findViewById(R.id.config_prog);

        currName = user.getDisplayName();
        currEmail = user.getEmail();
        currBio = MainMethods.getInstance().getAuxUser().getUser_bio();

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
        FirebaseAuth.getInstance().sendPasswordResetEmail(user.getEmail());
        Utils.showMessage("Foi enviado um email para " + user.getEmail() + "\n Verifique a sua caixa de entrada e siga as instruções.");
    }

    private void saveInformation(EditText name, EditText email, EditText bio, final FirebaseUser user) {
        String nameText, emailText, bioText;

        nameText = name.getText().toString();
        emailText = email.getText().toString();
        bioText = bio.getText().toString();

        if (!(emailText.contains("@") && (emailText.contains(".com") || emailText.contains(".pt")))) {
            Utils.showMessage("Insira um email válido!");
            return;
        }

        if (!(nameText.equals(currName) && emailText.equals(currEmail) && bioText.equals(currBio)))
            new ConfigHandler(this, nameText, emailText, bioText).execute();
        else
            showResults("Nenhuma mudança detectada!");

    }

    public void showResults(String messages) {
        Utils.showMessage(messages);
        MenuUtils.filterMenuItem(R.id.menu_perfil);
    }

    public String getCurrName() {
        return currName;
    }

    public String getCurrEmail() {
        return currEmail;
    }

    public String getCurrBio() {
        return currBio;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public ProgressBar getLoadingBar() {
        return loadingBar;
    }
}
