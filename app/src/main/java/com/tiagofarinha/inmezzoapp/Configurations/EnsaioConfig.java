package com.tiagofarinha.inmezzoapp.Configurations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;

public class EnsaioConfig extends Fragment {

    private Ensaio ensaio;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensaio_add, container, false);

        ensaio = (Ensaio) getArguments().getSerializable("ensaio");

        TextView tittle, date, hour, descr;

        tittle = view.findViewById(R.id.ensaio_add_title);
        date = view.findViewById(R.id.ensaio_date);
        hour = view.findViewById(R.id.ensaio_hour);
        descr = view.findViewById(R.id.ensaio_descr);

        Button add, delete;

        add = view.findViewById(R.id.ensaio_button);
        delete = view.findViewById(R.id.ensaio_delete);

        add.setText(R.string.update_button);
        delete.setVisibility(View.VISIBLE);

        tittle.setText("Editar Ensaio");

        String[] dateText = ensaio.getDate().split(",");
        date.setText(dateText[0]);
        hour.setText(dateText[1]);

        descr.setText(ensaio.getDescr());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

}
