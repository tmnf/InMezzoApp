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

public class ReservesLogic extends Fragment {

    private ArrayList<EditText> info;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.reserves_fragment, container, false);

        EditText name, email, local, data, msg;

        name = view.findViewById(R.id.nome_reserva_field);
        email = view.findViewById(R.id.email_reserva_field);
        local = view.findViewById(R.id.local_reserva_field);
        data = view.findViewById(R.id.data_reserva_field);
        msg = view.findViewById(R.id.mensagem_reserva_field);

        info = new ArrayList<>();
        info.add(name);
        info.add(email);
        info.add(local);
        info.add(data);
        info.add(msg);

        Button submit = view.findViewById(R.id.reserve_button);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (EditText x : info)
                    if (x.getText().toString().isEmpty()) {
                        Utils.showMessage("Campos vazios ou inválidos");
                        return;
                    }
                sendEmail();
            }
        });

        return view;
    }

    public void sendEmail() {
        String subject = ClientEmailUtils.getFormatedSubject(info.get(0).getText().toString(), ClientEmailUtils.RESERVE);
        String body = ClientEmailUtils.getFormatedReserveBody(info.get(0).getText().toString(), info.get(1).getText().toString(), info.get(2).getText().toString(),
                info.get(3).getText().toString(), info.get(4).getText().toString());

        new ClientEmailHandler(subject, body).start();

        clearFields();
    }

    public void clearFields() {
        for (EditText x : info)
            x.setText("");
    }
}
