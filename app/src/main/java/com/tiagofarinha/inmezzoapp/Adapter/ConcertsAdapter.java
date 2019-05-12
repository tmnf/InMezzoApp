package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

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

        Concert ensaio = concerts.get(position);

        // METER DADOS NA VIEW //

        return concertsView;
    }
}
