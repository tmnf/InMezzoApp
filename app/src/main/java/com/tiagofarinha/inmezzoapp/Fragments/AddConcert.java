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
import com.tiagofarinha.inmezzoapp.Dialogs.CalendarAux;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

import java.util.ArrayList;

public class AddConcert extends Fragment {

    private EditText date, local, descr, hour;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.concert_add, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        date = view.findViewById(R.id.concert_date);
        hour = view.findViewById(R.id.concert_hour);
        local = view.findViewById(R.id.concert_local);
        descr = view.findViewById(R.id.concert_descr);

        Button add = view.findViewById(R.id.concert_button), calendar = view.findViewById(R.id.concert_callendar_button);

        final ArrayList<EditText> info = new ArrayList<>();
        info.add(date);
        info.add(hour);
        info.add(local);
        info.add(descr);

        calendar.setOnClickListener((v) -> CalendarAux.openDatePicker(date, hour));
        add.setOnClickListener(v -> addInfo(info));
    }

    private void addInfo(ArrayList<EditText> info) {

        for (EditText x : info) {
            if (x.getText().toString().isEmpty()) {
                Utils.showMessage("Por favor preencha todos os campos");
                return;
            }
        }

        String date_formated = date.getText().toString() + "," + hour.getText().toString();

        Concert concert = new Concert(date_formated, local.getText().toString(), descr.getText().toString());

        DatabaseReference concert_ref = FirebaseDatabase.getInstance().getReference().child("concerts");

        Utils.showMessage("A adicionar concerto...");
        concert_ref.push().setValue(concert).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                MenuUtils.filterMenuItem(R.id.menu_concertos);
                Utils.showMessage("Concerto Adicionado");
            }
        });
    }
}
