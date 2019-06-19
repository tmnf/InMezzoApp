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

import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class LoginLogic extends Fragment {

    private EditText email, pass;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_fragment, container, false);

        getComponents(view);

        return view;
    }

    private void getComponents(View view) {
        email = view.findViewById(R.id.email_login_field);
        pass = view.findViewById(R.id.password_login_field);

        Button login = view.findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMethods.getInstance().closeKeyboard();
                tryLogin(email.getText().toString(), pass.getText().toString());
            }
        });
    }

    private void tryLogin(String email, String password) {
        Utils.showMessage("A Iniciar Sessão...");

        if (email.isEmpty() || password.isEmpty()) {
            Utils.showMessage("Campos em branco");
            return;
        }

        LoginUtils.logInUser(email, password, this);
    }

    public void checkLoginSucess(boolean success, String message) {
        if (success)
            MainMethods.getInstance().handleLog(1);
        else
            Utils.showMessage("Erro: " + message);
    }

}
