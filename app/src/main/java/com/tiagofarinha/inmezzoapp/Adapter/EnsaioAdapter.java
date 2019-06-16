package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    protected View fillView(View convertView, ViewGroup parent, Adaptable obj) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView, (Ensaio) obj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /* DATA HANDLE */

        String[] datetime = ((Ensaio) obj).getDate().split(",");

        holder.date.setText(datetime[0]);
        holder.hour.setText(datetime[1]);
        holder.descr.setText(holder.ensaio.getDescr());

        return convertView;
    }

    private class ViewHolder {
        Ensaio ensaio;

        TextView date, hour, descr;

        public ViewHolder(View view, Ensaio ensaio) {
            this.ensaio = ensaio;

            date = view.findViewById(R.id.ensaio_date_show);
            hour = view.findViewById(R.id.ensaio_hour_show);
            descr = view.findViewById(R.id.ensaio_descr_show);
        }
    }
}
