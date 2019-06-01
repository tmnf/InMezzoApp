package com.tiagofarinha.inmezzoapp.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
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
    protected void fillView(View view, Adaptable obj) {
        User user = (User) obj;

        /* REFERENCES */

        ImageView user_pic = view.findViewById(R.id.member_pic);
        TextView user_name = view.findViewById(R.id.member_name);
        TextView user_age = view.findViewById(R.id.member_age);
        TextView user_voice = view.findViewById(R.id.member_voice);

        /* Data Handle */

        LoginUtils.putInto(user_pic, user);

        // APAGAR ISTO //
        if (user.getUser_mode() == 1)
            user_name.setTextColor(Color.YELLOW);
        else if (user.getUser_mode() == 2)
            user_name.setTextColor(Color.BLUE);

        user_name.setText(user.getUser_name());
        user_age.setText(DateUtils.getAge(user.getUser_birthday()) + " Anos");
        user_voice.setText(user.getUser_voice());
    }
}
