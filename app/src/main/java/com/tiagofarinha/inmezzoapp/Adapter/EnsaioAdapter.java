package com.tiagofarinha.inmezzoapp.Adapter;

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

    public EnsaioAdapter(ArrayList<Adaptable> objects, int layoutId) {
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

        Ensaio ensaio = (Ensaio) obj;
        String[] datetime = ensaio.getDate().split(",");

        holder.date.setText(datetime[0]);

        String hour = datetime[1] + "h";
        holder.hour.setText(hour);
        holder.descr.setText(ensaio.getDescr());

        return convertView;
    }


    private class ViewHolder {
        TextView date, hour, descr;

        private ViewHolder(View view) {
            date = view.findViewById(R.id.ensaio_date_show);
            hour = view.findViewById(R.id.ensaio_hour_show);
            descr = view.findViewById(R.id.ensaio_descr_show);
        }
    }
}
