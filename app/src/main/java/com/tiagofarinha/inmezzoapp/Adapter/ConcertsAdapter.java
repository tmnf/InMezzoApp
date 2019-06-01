package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class ConcertsAdapter extends DefaultAdapter {

    public ConcertsAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, objects, layoutId);
    }

    @Override
    protected void fillView(View view, Adaptable obj) {
        TextView descr, date, hour, local;

        Concert concert = (Concert) obj;

        descr = view.findViewById(R.id.concert_title_show);
        local = view.findViewById(R.id.concert_local_show);
        date = view.findViewById(R.id.concert_date_show);
        hour = view.findViewById(R.id.concert_hour_show);

        /* DATA HANDLE */

        descr.setText(concert.getDescr());
        local.setText("Local: " + concert.getLocal());

        String[] datetime = concert.getDate().split(",");

        date.setText("Dia: " + datetime[0]);
        hour.setText("Hora: " + datetime[1]);
    }
}
