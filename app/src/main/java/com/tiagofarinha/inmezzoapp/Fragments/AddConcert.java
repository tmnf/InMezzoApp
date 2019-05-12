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
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;

public class AddConcert extends Fragment {

    private EditText date, local, descr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.concert_add, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        date = view.findViewById(R.id.concert_date);
        local = view.findViewById(R.id.concert_local);
        descr = view.findViewById(R.id.concert_descr);

        Button add = view.findViewById(R.id.concert_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfo();
            }
        });
    }

    private void addInfo() {
        Concert concert = new Concert(date.getText().toString(), local.getText().toString(), descr.getText().toString());

        DatabaseReference concert_ref = FirebaseDatabase.getInstance().getReference().child("concerts");
        concert_ref.push().setValue(concert);
    }
}
