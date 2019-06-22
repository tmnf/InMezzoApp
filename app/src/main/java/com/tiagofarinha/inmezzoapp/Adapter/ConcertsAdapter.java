package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Fragments.PollLogic;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
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

        String aux;

        holder.descr.setText(concert.getDescr());

        aux = "Local: " + concert.getLocal();
        holder.local.setText(aux);

        String[] datetime = concert.getDate().split(",");

        aux = "Dia: " + datetime[0];
        holder.date.setText(aux);

        aux = "Hora: " + datetime[1];
        holder.hour.setText(aux);

        if (MainMethods.getInstance().isLoggedIn())
            addListener(convertView, concert);

        return convertView;
    }

    private void addListener(View convertView, final Concert concert) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new PollLogic();
                Bundle args = new Bundle();
                args.putSerializable("event", concert);
                frag.setArguments(args);
                MainMethods.getInstance().changeFrag(frag, 0, true);
            }
        });
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
