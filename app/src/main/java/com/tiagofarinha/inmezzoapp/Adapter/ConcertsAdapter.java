package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    protected View fillView(View convertView, ViewGroup parent, Adaptable obj) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /* DATA HANDLE */

        Concert concert = (Concert) obj;

        holder.descr.setText(concert.getDescr());
        holder.local.setText("Local: " + concert.getLocal());

        String[] datetime = concert.getDate().split(",");

        holder.date.setText("Dia: " + datetime[0]);
        holder.hour.setText("Hora: " + datetime[1]);

        return convertView;
    }

    private class ViewHolder {

        TextView descr, date, hour, local;

        public ViewHolder(View view) {
            descr = view.findViewById(R.id.concert_title_show);
            local = view.findViewById(R.id.concert_local_show);
            date = view.findViewById(R.id.concert_date_show);
            hour = view.findViewById(R.id.concert_hour_show);
        }
    }
}
