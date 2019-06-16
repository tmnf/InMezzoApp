package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;

/* This class handles music row fulfilling */

public class PortfolioAdapter extends DefaultAdapter {

    public PortfolioAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, objects, layoutId);
    }

    @Override
    protected View fillView(View convertView, ViewGroup parent, Adaptable obj) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView, (Music) obj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /* DATA HANDLE */
        holder.title.setText(holder.music.getName());
        holder.artist.setText("- " + holder.music.getArtist());

        return convertView;
    }

    private class ViewHolder {
        Music music;

        TextView title, artist;

        public ViewHolder(View view, Music music) {
            this.music = music;

            title = view.findViewById(R.id.portfolio_title_show);
            artist = view.findViewById(R.id.portfolio_artist_show);
        }
    }
}
