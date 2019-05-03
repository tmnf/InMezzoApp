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

import com.tiagofarinha.inmezzoapp.Comunication.EmailHandler;
import com.tiagofarinha.inmezzoapp.Comunication.EmailTextUtils;
import com.tiagofarinha.inmezzoapp.R;

public class ReservesLogic extends Fragment {

    private EditText name, email, local, data, msg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reserves_fragment, container, false);

        name = view.findViewById(R.id.nome_reserva_field);
        email = view.findViewById(R.id.email_reserva_field);
        local = view.findViewById(R.id.local_reserva_field);
        data = view.findViewById(R.id.data_reserva_field);
        msg = view.findViewById(R.id.mensagem_reserva_field);

        Button submit = view.findViewById(R.id.reserve_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendEmail();
            }
        });

        return view;
    }

    public void sendEmail(){
        String subject = EmailTextUtils.getFormatedSubject(name.getText().toString(), EmailTextUtils.RESERVE);
        String body = EmailTextUtils.getFormatedReserveBody(name.getText().toString(), email.getText().toString(), local.getText().toString(), data.getText().toString(), msg.getText().toString());

        new EmailHandler(subject, body).start();

        clearFields();
    }

    public void clearFields() {
        name.setText("");
        email.setText("");
        local.setText("");
        data.setText("");
        msg.setText("");
    }
}
