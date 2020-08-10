package com.tiagofarinha.inmezzoapp.MainLogic;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.Models.PicInfo;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

public class PicView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pic_view);

        ImageView pic = findViewById(R.id.pic_view);

        User user = (User) getIntent().getExtras().getSerializable("user");

        if (user == null) {
            int image_id = getIntent().getExtras().getInt("img_id");
            Picasso.get().load(image_id).fit().centerInside().into(pic);
        } else
            LoginUtils.putInto(pic, user);
    }


}