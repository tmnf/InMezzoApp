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

import com.tiagofarinha.inmezzoapp.Email.EmailHandler;
import com.tiagofarinha.inmezzoapp.Email.EmailUtils;
import com.tiagofarinha.inmezzoapp.R;

public class CandidaturasLogic extends Fragment {

    private EditText name, birth, email, phone;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.candidaturas_fragment, container, false);

        name = view.findViewById(R.id.nome_candi_field);
        birth = view.findViewById(R.id.data_candi_field);
        email = view.findViewById(R.id.email_candi_field);
        phone = view.findViewById(R.id.telemovel_candi_field);

        Button submit  = view.findViewById(R.id.candidaturas_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendMail();
            }
        });

        return view;
    }

    public void sendMail(){
        String subject = EmailUtils.getFormatedSubject(name.getText().toString(), EmailUtils.CANDIDATURE);
        String message = EmailUtils.getFormatedCandidatureBody(name.getText().toString(), birth.getText().toString(), email.getText().toString(), phone.getText().toString());

        new EmailHandler(subject,message).start();

        cleanScreen();
    }

    private void cleanScreen() {
        name.setText("");
        birth.setText("");
        email.setText("");
        phone.setText("");
    }
}
