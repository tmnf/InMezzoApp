package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;

import java.util.ArrayList;

/* This class abstracts row fulfilling */

public abstract class DefaultAdapter extends ArrayAdapter<Adaptable> {

    protected Context mContext;
    protected ArrayList<Adaptable> objects;
    protected int layoutId;

    public DefaultAdapter(ArrayList<Adaptable> objects, int layoutId) {
        super(MainMethods.getInstance().getContext(), 0, objects);

        this.mContext = MainMethods.getInstance().getContext();
        this.objects = objects;
        this.layoutId = layoutId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Adaptable obj = objects.get(position);

        return fillView(convertView, parent, obj);
    }

    protected abstract View fillView(View view, ViewGroup parent, Adaptable obj);
}
