package com.tiagofarinha.inmezzoapp.Adapter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Configurations.MusicConfig;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;

/* This class handles music row fulfilling */

public class PortfolioAdapter extends DefaultAdapter {

    public PortfolioAdapter(ArrayList<Adaptable> objects, int layoutId) {
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

        final Music music = (Music) obj;

        /* DATA HANDLE */
        holder.title.setText(music.getName());

        String artist = "- " + music.getArtist();
        holder.artist.setText(artist);

        if (MainMethods.getInstance().isOp())
            addListener(convertView, music);

        return convertView;
    }

    private void addListener(View convertView, final Music music) {
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Fragment frag = new MusicConfig();

                Bundle bundle = new Bundle();
                bundle.putSerializable("music", music);

                frag.setArguments(bundle);

                MainMethods.getInstance().changeFrag(frag, R.id.menu_portfolio, true);
                return true;
            }
        });
    }

    private class ViewHolder {
        TextView title, artist;

        private ViewHolder(View view) {
            title = view.findViewById(R.id.portfolio_title_show);
            artist = view.findViewById(R.id.portfolio_artist_show);
        }
    }
}
