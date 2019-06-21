package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class WarningsAdapter extends DefaultAdapter {

    public WarningsAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
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

        Warning warning = (Warning) obj;

        holder.warning_title.setText(warning.getTitle());
        holder.warning_text.setText(warning.getText());
        holder.warning_user.setText(warning.getUser().getUser_name() + ", " + warning.getPub_date().split(" ")[0]);

        return convertView;
    }

    private class ViewHolder {
        TextView warning_text, warning_title, warning_user;

        public ViewHolder(View view) {
            warning_text = view.findViewById(R.id.warning_text);
            warning_title = view.findViewById(R.id.warning_title);
            warning_user = view.findViewById(R.id.warning_user);
        }
    }
}
