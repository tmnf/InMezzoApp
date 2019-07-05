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
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
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
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        User user = (User) obj;

        /* Data Handle */
        LoginUtils.putInto(holder.user_pic, user);

        // APAGAR ISTO //
        if (user.getUser_mode() == 1)
            holder.user_name.setTextColor(Color.YELLOW);
        else if (user.getUser_mode() == 2)
            holder.user_name.setTextColor(Color.BLUE);
        else
            holder.user_name.setTextColor(Color.WHITE);

        holder.user_name.setText(user.getUser_name());

        String age = DateUtils.getAge(user.getUser_birthday()) + " Anos";

        holder.user_age.setText(age);

        holder.user_voice.setText(user.getUser_voice());

        addListener(holder, user);

        return convertView;
    }

    private void addListener(final ViewHolder h, final User user) {
        h.user_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMethods.getInstance().goToProfilePage(user);
            }
        });
    }

    private class ViewHolder {
        ImageView user_pic;
        TextView user_name, user_age, user_voice;

        private ViewHolder(View view) {
            user_pic = view.findViewById(R.id.member_pic);
            user_name = view.findViewById(R.id.member_name);
            user_age = view.findViewById(R.id.member_age);
            user_voice = view.findViewById(R.id.member_voice);
        }
    }
}
