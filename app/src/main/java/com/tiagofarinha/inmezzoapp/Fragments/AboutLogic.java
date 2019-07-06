package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.tiagofarinha.inmezzoapp.R;

public class AboutLogic extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.about_fragment, container, false);

        ImageView pic = view.findViewById(R.id.coro_image);

        Picasso.get().load(R.drawable.inmezzo_photo).fit().centerInside().into(pic);

        return view;
    }

}
