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

public class ReservesLogic extends Fragment {

    private EditText name, email, local, data, msg;
    private ArrayList<String> info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reserves_fragment, container, false);

        name = view.findViewById(R.id.nome_reserva_field);
        email = view.findViewById(R.id.email_reserva_field);
        local = view.findViewById(R.id.local_reserva_field);
        data = view.findViewById(R.id.data_reserva_field);
        msg = view.findViewById(R.id.mensagem_reserva_field);

        info = new ArrayList<>();
        info.add(name.getText().toString());
        info.add(email.getText().toString());
        info.add(local.getText().toString());
        info.add(data.getText().toString());
        info.add(msg.getText().toString());

        Button submit = view.findViewById(R.id.reserve_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (String x : info)
                    if (x.isEmpty()) {
                        Utils.showMessage("Campos vazios ou inválidos");
                        return;
                    }
                sendEmail();
            }
        });

        return view;
    }

    public void sendEmail() {
        String subject = ClienteEmailUtils.getFormatedSubject(info.get(0), ClienteEmailUtils.RESERVE);
        String body = ClienteEmailUtils.getFormatedReserveBody(info.get(0), info.get(1), info.get(2), info.get(3), info.get(4));

        new ClientEmailHandler(subject, body).start();

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
