package com.tiagofarinha.inmezzoapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.MainLogic.PicView;
import com.tiagofarinha.inmezzoapp.R;

public class AboutLogic extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        ImageView pic = view.findViewById(R.id.coro_image);

        int img_id = R.drawable.inmezzo_photo;

        Picasso.get().load(img_id).fit().centerInside().into(pic);

        pic.setOnClickListener(view1 -> {
            Intent intent = new Intent(MainMethods.getInstance().getContext(), PicView.class);
            Bundle b = new Bundle();
            b.putSerializable("img_id", img_id);
            intent.putExtras(b);
            startActivity(intent);
        });

        return view;
    }

}
