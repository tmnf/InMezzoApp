package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;

/* This class handles member row fulfilling */

public class UserAdapter extends DefaultAdapter {

    public UserAdapter(@NonNull Context mContext, ArrayList<Adaptable> objects, int layoutId) {
        super(mContext, objects, layoutId);
    }

    @Override
    protected View fillView(View convertView, ViewGroup parent, Adaptable obj) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            holder = new ViewHolder(convertView, (User) obj);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        /* Data Handle */
        LoginUtils.putInto(holder.user_pic, holder.user);

        // APAGAR ISTO //
        if (holder.user.getUser_mode() == 1)
            holder.user_name.setTextColor(Color.YELLOW);
        else if (holder.user.getUser_mode() == 2)
            holder.user_name.setTextColor(Color.BLUE);

        holder.user_name.setText(holder.user.getUser_name());
        holder.user_age.setText(DateUtils.getAge(holder.user.getUser_birthday()) + " Anos");
        holder.user_voice.setText(holder.user.getUser_voice());

        addListener(holder);

        return convertView;
    }

    private void addListener(final ViewHolder h) {
        h.user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().goToProfilePage(h.user);
            }
        });
    }

    private class ViewHolder {
        User user;

        ImageView user_pic;
        TextView user_name, user_age, user_voice;

        public ViewHolder(View view, User user) {
            this.user = user;

            user_pic = view.findViewById(R.id.member_pic);
            user_name = view.findViewById(R.id.member_name);
            user_age = view.findViewById(R.id.member_age);
            user_voice = view.findViewById(R.id.member_voice);
        }
    }
}
