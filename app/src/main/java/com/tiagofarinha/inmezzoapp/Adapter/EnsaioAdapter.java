package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class EnsaioAdapter extends ArrayAdapter<Ensaio> {

    private Context mContext;
    private ArrayList<Ensaio> ensaios;

    public EnsaioAdapter(@NonNull Context mContext, ArrayList<Ensaio> ensaios) {
        super(mContext, 0, ensaios);

        this.mContext = mContext;
        this.ensaios = ensaios;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ensaiosView = convertView;
        if (ensaiosView == null)
            ensaiosView = LayoutInflater.from(mContext).inflate(R.layout.ensaio_row, parent, false);

        Ensaio ensaio = ensaios.get(position);

        // METER DADOS NA VIEW //

        TextView date, hour, descr;

        date = ensaiosView.findViewById(R.id.ensaio_date_show);
        hour = ensaiosView.findViewById(R.id.ensaio_hour_show);
        descr = ensaiosView.findViewById(R.id.ensaio_descr_show);

        String[] datetime = ensaio.getDate().split(",");

        date.setText(datetime[0]);
        hour.setText(datetime[1]);
        descr.setText(ensaio.getDescr());

        return ensaiosView;
    }
}
