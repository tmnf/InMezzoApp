package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class EnsaioAdapter extends ArrayAdapter<Ensaio> {

    private Context mContext;
    private ArrayList<Ensaio> ensaios;

    public EnsaioAdapter(@NonNull Context mContext, ArrayList<Ensaio> users) {
        super(mContext, 0, users);

        this.mContext = mContext;
        this.ensaios = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View ensaiosView = convertView;
        if (ensaiosView == null)
            ensaiosView = LayoutInflater.from(mContext).inflate(R.layout.user_row, parent, false);

        Ensaio ensaio = ensaios.get(position);

        // METER DADOS NA VIEW //

        return ensaiosView;
    }
}
