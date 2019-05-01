package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.AdminTools.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;
import com.tiagofarinha.inmezzoapp.Utils.MathUtils;

import java.util.ArrayList;


public class UserAdapter extends ArrayAdapter<User> {

    private Context mContext;
    private ArrayList<User> users;

    public UserAdapter(@NonNull Context mContext, ArrayList<User> users) {
        super(mContext, 0, users);

        this.mContext = mContext;
        this.users = users;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listUser = convertView;
        if(listUser == null)
            listUser = LayoutInflater.from(mContext).inflate(R.layout.user_row,parent,false);

        User user = users.get(position);

        ImageView user_pic = listUser.findViewById(R.id.member_pic);

        TextView user_name = listUser.findViewById(R.id.member_name);
        TextView user_age = listUser.findViewById(R.id.member_age);
        TextView user_voice = listUser.findViewById(R.id.member_voice);

        LoginUtils.getPic(user_pic, user);

        // APAGAR ISTO //
        if(user.getUser_mode() == 1)
            user_name.setTextColor(Color.YELLOW);

        user_name.setText(user.getUser_name());
        user_age.setText(MathUtils.getAge(user.getUser_birthday()) + " Anos");
        user_voice.setText(user.getUser_voice());


        return listUser;
    }
}
