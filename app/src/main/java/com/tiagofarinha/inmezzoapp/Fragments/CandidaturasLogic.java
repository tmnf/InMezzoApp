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

import com.tiagofarinha.inmezzoapp.Comunication.ClientEmailHandler;
import com.tiagofarinha.inmezzoapp.Comunication.ClienteEmailUtils;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class CandidaturasLogic extends Fragment {

    private EditText name, birth, email, phone;
    private ArrayList<String> info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.candidaturas_fragment, container, false);

        name = view.findViewById(R.id.nome_candi_field);
        birth = view.findViewById(R.id.data_candi_field);
        email = view.findViewById(R.id.email_candi_field);
        phone = view.findViewById(R.id.telemovel_candi_field);

        info = new ArrayList<>();
        info.add(name.getText().toString());
        info.add(birth.getText().toString());
        info.add(email.getText().toString());
        info.add(phone.getText().toString());

        Button submit = view.findViewById(R.id.candidaturas_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String x : info)
                    if (x.isEmpty()) {
                        Utils.showMessage(getContext(), "Campos vazios ou inválidos!");
                        return;
                    }

                sendMail();
            }
        });

        return view;
    }

    public void sendMail() {
        String subject = ClienteEmailUtils.getFormatedSubject(info.get(0), ClienteEmailUtils.CANDIDATURE);
        String message = ClienteEmailUtils.getFormatedCandidatureBody(info.get(0), info.get(1), info.get(2), info.get(3));

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
