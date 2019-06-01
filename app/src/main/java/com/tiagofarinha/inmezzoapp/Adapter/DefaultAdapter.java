package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

import java.util.ArrayList;

/* This class abstracts row fulfilling */

public abstract class DefaultAdapter extends ArrayAdapter<Adaptable> {

    protected Context mContext;
    protected ArrayList<Adaptable> objects;
    protected int layoutId;

    public DefaultAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, 0, objects);

        this.mContext = mContext;
        this.objects = objects;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View objListView = convertView;

        if (objListView == null)
            objListView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);

        Adaptable obj = objects.get(position);

        fillView(objListView, obj);

        return objListView;
    }

    protected abstract void fillView(View view, Adaptable obj);
}
