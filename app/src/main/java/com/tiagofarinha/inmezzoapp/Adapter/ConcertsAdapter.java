package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class ConcertsAdapter extends ArrayAdapter<Concert> {

    private Context mContext;
    private ArrayList<Concert> concerts;

    public ConcertsAdapter(@NonNull Context mContext, ArrayList<Concert> concerts) {
        super(mContext, 0, concerts);

        this.mContext = mContext;
        this.concerts = concerts;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View concertsView = convertView;
        if (concertsView == null)
            concertsView = LayoutInflater.from(mContext).inflate(R.layout.concert_row, parent, false);

        Concert concert = concerts.get(position);

        // METER DADOS NA VIEW //

        TextView descr, date, hour, local;

        descr = concertsView.findViewById(R.id.concert_title_show);
        local = concertsView.findViewById(R.id.concert_local_show);
        date = concertsView.findViewById(R.id.concert_date_show);
        hour = concertsView.findViewById(R.id.concert_hour_show);

        descr.setText(concert.getDescr());
        local.setText("Local: " + concert.getLocal());

        String[] datetime = concert.getDate().split(",");

        date.setText("Dia: " + datetime[0]);
        hour.setText("Hora: " + datetime[1]);

        return concertsView;
    }
}
