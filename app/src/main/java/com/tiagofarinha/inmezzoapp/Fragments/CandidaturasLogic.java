package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Comunication.ClientEmailHandler;
import com.tiagofarinha.inmezzoapp.Comunication.ClientEmailUtils;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class CandidaturasLogic extends Fragment {

    private EditText name, birth, email, phone;
    private ArrayList<EditText> info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.candidaturas_fragment, container, false);

        name = view.findViewById(R.id.nome_candi_field);
        birth = view.findViewById(R.id.data_candi_field);
        email = view.findViewById(R.id.email_candi_field);
        phone = view.findViewById(R.id.telemovel_candi_field);

        info = new ArrayList<>();
        info.add(name);
        info.add(birth);
        info.add(email);
        info.add(phone);

        Button submit = view.findViewById(R.id.candidaturas_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText x : info)
                    if (x.getText().toString().isEmpty()) {
                        Utils.showMessage("Campos vazios ou inválidos!");
                        return;
                    }

                sendMail();
            }
        });

        return view;
    }

    public void sendMail() {
        String subject = ClientEmailUtils.getFormatedSubject(info.get(0).getText().toString(), ClientEmailUtils.CANDIDATURE);
        String message = ClientEmailUtils.getFormatedCandidatureBody(info.get(0).getText().toString(), info.get(1).getText().toString(),
                info.get(2).getText().toString(), info.get(3).getText().toString());

        new ClientEmailHandler(subject, message).start();

        clearFields();
    }

    private void clearFields() {
        name.setText("");
        birth.setText("");
        email.setText("");
        phone.setText("");
    }
}
