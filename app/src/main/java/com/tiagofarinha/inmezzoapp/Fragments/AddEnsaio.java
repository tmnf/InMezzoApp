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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;

public class AddEnsaio extends Fragment {

    private EditText date, descr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensaio_add, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        EditText date, descr;

        date = view.findViewById(R.id.ensaio_date);
        descr = view.findViewById(R.id.ensaio_descr);

        Button add = view.findViewById(R.id.ensaio_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfo();
            }
        });
    }

    private void addInfo() {
        Ensaio ensaio = new Ensaio(date.getText().toString(), descr.getText().toString());
        DatabaseReference ensaio_ref = FirebaseDatabase.getInstance().getReference().child("ensaios");
        ensaio_ref.push().setValue(ensaio);
    }

}
