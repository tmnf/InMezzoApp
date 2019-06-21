package com.tiagofarinha.inmezzoapp.Fragments;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;

public class ProfileLogic extends Fragment {

    private ImageView pic;
    private TextView name, age, voice, settings, bio;

    public ProfileLogic() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.profile_fragment, container, false);

        pic = view.findViewById(R.id.profile_pic);
        name = view.findViewById(R.id.profile_name);
        age = view.findViewById(R.id.profile_age);
        voice = view.findViewById(R.id.profile_voice);
        bio = view.findViewById(R.id.profile_bio);

        settings = view.findViewById(R.id.profile_configs);

        if (getArguments() == null)
            getUser();
        else {
            settings.setVisibility(View.GONE);

            User user = (User) getArguments().getSerializable("user");
            refreshGUI(user);
        }

        return view;
    }

    public void getUser() {
        FirebaseUser aut = FirebaseAuth.getInstance().getCurrentUser();
        if (aut != null) {
            FirebaseDatabase.getInstance().getReference().child("users").child(aut.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    refreshGUI(dataSnapshot.getValue(User.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }

    public void refreshGUI(User user) {
        LoginUtils.putInto(pic, user);
        name.setText(user.getUser_name());
        age.setText("Idade: " + DateUtils.getAge(user.getUser_birthday()) + " Anos");
        voice.setText("Voz: " + user.getUser_voice());
        bio.setText(user.getUser_bio());

        addConfigListener();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void addConfigListener() {
        settings.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        settings.setTextColor(Color.BLACK);
                        break;
                    case MotionEvent.ACTION_UP:
                        settings.setTextColor(Color.WHITE);
                        MainMethods.getInstance().changeFrag(new ConfigLogic(), R.id.menu_perfil, true);
                        break;
                }
                return true;
            }
        });
    }

}
