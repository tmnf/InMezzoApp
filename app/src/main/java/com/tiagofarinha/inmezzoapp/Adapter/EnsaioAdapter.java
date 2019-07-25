package com.tiagofarinha.inmezzoapp.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Configurations.EnsaioConfig;
import com.tiagofarinha.inmezzoapp.Fragments.PollLogic;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
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

        addListener(convertView, ensaio);

        return convertView;
    }

    private void addListener(View convertView, final Ensaio ensaio) {
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment frag = new PollLogic();
                Bundle args = new Bundle();
                args.putSerializable("event", ensaio);
                frag.setArguments(args);
                MainMethods.getInstance().changeFrag(frag, R.id.menu_ensaios, true);
            }
        });

        if (MainMethods.getInstance().isOp())
            convertView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    Fragment frag = new EnsaioConfig();

                    Bundle bundle = new Bundle();
                    bundle.putSerializable("ensaio", ensaio);

                    frag.setArguments(bundle);

                    MainMethods.getInstance().changeFrag(frag, R.id.menu_ensaios, true);
                    return true;
                }
            });
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
