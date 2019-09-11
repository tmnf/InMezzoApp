package com.tiagofarinha.inmezzoapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.R;

import java.util.ArrayList;


public class WarningsAdapter extends DefaultAdapter {

    public WarningsAdapter(ArrayList<Adaptable> objects, int layoutId) {
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

        Warning warning = (Warning) obj;

        if (warning.getLink() != null)
            if (!warning.getLink().isEmpty())
                holder.warning_title.setTextColor(Color.YELLOW);
            else
                holder.warning_title.setTextColor(Color.WHITE);
        else
            holder.warning_title.setTextColor(Color.WHITE);

        holder.warning_title.setText(warning.getTitle());
        holder.warning_text.setText(warning.getText());

        String user = warning.getUser().getUser_name() + ", " + warning.getPub_date().split(" ")[0];
        holder.warning_user.setText(user);

        return convertView;
    }

    private class ViewHolder {
        TextView warning_text, warning_title, warning_user;

        private ViewHolder(View view) {
            warning_text = view.findViewById(R.id.warning_text);
            warning_title = view.findViewById(R.id.warning_title);
            warning_user = view.findViewById(R.id.warning_user);
        }
    }
}
