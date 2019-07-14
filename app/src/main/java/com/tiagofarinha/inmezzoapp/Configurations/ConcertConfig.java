package com.tiagofarinha.inmezzoapp.Configurations;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;

public class ConcertConfig extends Fragment {

    private Concert concert;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.concert_add, container, false);

        concert = (Concert) getArguments().getSerializable("concert");

        TextView tittle, date, hour, local, descr;
        Button add, delete;

        tittle = view.findViewById(R.id.concert_add_title);
        date = view.findViewById(R.id.concert_date);
        hour = view.findViewById(R.id.concert_hour);
        local = view.findViewById(R.id.concert_local);
        descr = view.findViewById(R.id.concert_descr);

        add = view.findViewById(R.id.concert_button);
        delete = view.findViewById(R.id.concert_delete);


        tittle.setText("Editar Concerto");

        String[] dateText = concert.getDate().split(",");
        date.setText(dateText[0]);
        hour.setText(dateText[1]);
        local.setText(concert.getLocal());
        descr.setText(concert.getDescr());

        add.setText(R.string.update_button);
        delete.setVisibility(View.VISIBLE);

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
