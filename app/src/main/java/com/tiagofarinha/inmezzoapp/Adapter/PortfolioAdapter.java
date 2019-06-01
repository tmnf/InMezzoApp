package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
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
    protected void fillView(View view, Adaptable obj) {
        Music music = (Music) obj;

        /* REFERENCES */

        TextView title, artist;

        title = view.findViewById(R.id.portfolio_title_show);
        artist = view.findViewById(R.id.portfolio_artist_show);

        /* DATA HANDLE */

        title.setText(music.getName());
        artist.setText("- " + music.getArtist());
    }
}
