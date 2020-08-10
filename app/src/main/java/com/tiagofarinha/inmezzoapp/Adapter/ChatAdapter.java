package com.tiagofarinha.inmezzoapp.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.ChatMessage;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

import java.util.ArrayList;

/* This class handles post row fulfilling */

public class ChatAdapter extends DefaultAdapter {

    public ChatAdapter(ArrayList<Adaptable> objects, int layoutId) {
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

        ChatMessage message = (ChatMessage) obj;

        LoginUtils.putInto(holder.pic, message.getUser());

        if (MainMethods.getInstance().getAuxUser().equals(message.getUser()))
            holder.chat_name.setTextColor(Color.YELLOW);
        else
            holder.chat_name.setTextColor(Color.WHITE);

        holder.chat_name.setText(message.getUser().getUser_name());
        holder.chat_pub_date.setText(message.getPub_date());
        holder.chat_text.setText(message.getMsg());

        addUserListener(holder, message);

        return convertView;
    }

    private void addUserListener(final ViewHolder h, final ChatMessage message) {
        User user = ResourceLoader.getInstance().getUserByPhone(message.getUser().getUser_phone());
        if (user != null)
            h.pic.setOnClickListener(v -> MainMethods.getInstance().goToProfilePage(user));
    }

    /* Caching Views */
    private class ViewHolder {
        TextView chat_name, chat_pub_date, chat_text;
        ImageView pic;

        ViewHolder(View convertView) {
            pic = convertView.findViewById(R.id.chat_pic);
            chat_name = convertView.findViewById(R.id.chat_name);
            chat_pub_date = convertView.findViewById(R.id.chat_pub_date);
            chat_text = convertView.findViewById(R.id.chat_message);
        }
    }

}
