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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class AddEnsaio extends Fragment {

    private EditText date, hour, descr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensaio_add, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        date = view.findViewById(R.id.ensaio_date);
        hour = view.findViewById(R.id.ensaio_hour);
        descr = view.findViewById(R.id.ensaio_descr);

        final ArrayList<EditText> fields = new ArrayList<>();
        fields.add(date);
        fields.add(hour);
        fields.add(descr);

        Button add = view.findViewById(R.id.ensaio_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfo(fields);
            }
        });
    }

    private void addInfo(ArrayList<EditText> fields) {
        for (EditText x : fields)
            if (x.getText().toString().isEmpty()) {
                Utils.showMessage("Por favor preencha todos os campos");
                return;
            }

        String date_formated = date.getText().toString() + "," + hour.getText().toString();

        Ensaio ensaio = new Ensaio(date_formated, descr.getText().toString());

        DatabaseReference ensaio_ref = FirebaseDatabase.getInstance().getReference().child("ensaios");

        Utils.showMessage("A adicionar ensaio...");

        ensaio_ref.push().setValue(ensaio).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                MenuUtils.filterMenuItem(R.id.menu_ensaios);
                Utils.showMessage("Ensaio Adicionado com Sucesso!");
            }
        });
    }

}
