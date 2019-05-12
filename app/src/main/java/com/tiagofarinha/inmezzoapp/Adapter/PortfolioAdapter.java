package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class PortfolioAdapter extends ArrayAdapter<Music> {

    private Context mContext;
    private ArrayList<Music> portfolio;

    public PortfolioAdapter(@NonNull Context mContext, ArrayList<Music> portfolio) {
        super(mContext, 0, portfolio);

        this.mContext = mContext;
        this.portfolio = portfolio;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View portfolioView = convertView;
        if (portfolioView == null)
            portfolioView = LayoutInflater.from(mContext).inflate(R.layout.portfolio_row, parent, false);

        Music music = portfolio.get(position);

        // METER DADOS NA VIEW //

        return portfolioView;
    }
}
