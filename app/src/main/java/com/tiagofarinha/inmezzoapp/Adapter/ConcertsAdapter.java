package com.tiagofarinha.inmezzoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Concert;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class ConcertsAdapter extends DefaultAdapter {

    public ConcertsAdapter(ArrayList<Adaptable> objects, int layoutId) {
        super(objects, layoutId);
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

        String aux;

        holder.descr.setText(concert.getDescr());

        aux = "Local: " + concert.getLocal();
        holder.local.setText(aux);

        String[] datetime = concert.getDate().split(",");

        aux = "Dia: " + datetime[0];
        holder.date.setText(aux);

        aux = "Hora: " + datetime[1] + "h";
        holder.hour.setText(aux);

        return convertView;
    }


    private class ViewHolder {

        TextView descr, date, hour, local;

        private ViewHolder(View view) {
            descr = view.findViewById(R.id.concert_title_show);
            local = view.findViewById(R.id.concert_local_show);
            date = view.findViewById(R.id.concert_date_show);
            hour = view.findViewById(R.id.concert_hour_show);
        }
    }
}
