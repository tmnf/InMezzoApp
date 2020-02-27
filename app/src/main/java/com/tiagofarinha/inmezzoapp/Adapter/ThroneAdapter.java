package com.tiagofarinha.inmezzoapp.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;

/* This class handles member row fulfilling */

public class ThroneAdapter extends DefaultAdapter {

    public ThroneAdapter(ArrayList<Adaptable> objects, int layoutId) {
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

        User user = (User) obj;

        /* Data Handle */
        LoginUtils.putInto(holder.throne_pic, user);

        holder.throne_bar.setMax(ResourceLoader.getInstance().getTotalVotes());
        holder.throne_bar.setProgress(user.getVotes());

        String votes = user.getVotes() + " votos";
        holder.throne_votes.setText(votes);

        return convertView;
    }

    private class ViewHolder {
        ImageView throne_pic;
        ProgressBar throne_bar;
        TextView throne_votes;

        private ViewHolder(View view) {
            throne_pic = view.findViewById(R.id.throne_pic);
            throne_bar = view.findViewById(R.id.throne_bar);
            throne_votes = view.findViewById(R.id.throne_votes);
        }
    }
}
