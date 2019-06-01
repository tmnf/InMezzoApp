package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;

/* This class handles member row fulfilling */
public class EnsaioAdapter extends DefaultAdapter {

    public EnsaioAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, objects, layoutId);
    }

    @Override
    protected void fillView(View view, Adaptable obj) {
        TextView date, hour, descr;

        Ensaio ensaio = (Ensaio) obj;

        date = view.findViewById(R.id.ensaio_date_show);
        hour = view.findViewById(R.id.ensaio_hour_show);
        descr = view.findViewById(R.id.ensaio_descr_show);

        /* DATA HANDLE */

        String[] datetime = ((Ensaio) obj).getDate().split(",");

        date.setText(datetime[0]);
        hour.setText(datetime[1]);
        descr.setText(ensaio.getDescr());
    }
}
