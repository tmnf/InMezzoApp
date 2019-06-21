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
import android.widget.TextView;

import com.tiagofarinha.inmezzoapp.Comunication.ContactsIntentHandler;
import com.tiagofarinha.inmezzoapp.R;

public class ContactsLogic extends Fragment {

    public static final String YOUTUBE_URL = "https://www.youtube.com/channel/UCfd0p-mr1n2jgECeL-nPLgw";
    public static final String FACEBOOK_URL = "https://www.facebook.com/InMezzoCoro/";
    public static final String INSTAGRAM_URL = "inmezzo.orfeao";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);

        getComps(view);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getComps(View view) {

        final TextView email, phone1, phone2, facebook, instagram, youtube;

        email = view.findViewById(R.id.email_texto);
        phone1 = view.findViewById(R.id.contacto1_texto);
        phone2 = view.findViewById(R.id.contacto2_texto);

        facebook = view.findViewById(R.id.facebook_texto);
        instagram = view.findViewById(R.id.instagram_texto);
        youtube = view.findViewById(R.id.youtube_texto);


        facebook.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, facebook, ContactsIntentHandler.FACEBOOK);
                return true;
            }
        });

        instagram.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, instagram, ContactsIntentHandler.INSTAGRAM);
                return false;
            }
        });

        youtube.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, youtube, ContactsIntentHandler.YOUTUBE);
                return true;
            }
        });

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, email, ContactsIntentHandler.EMAIL);
                return true;
            }
        });

        phone1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, phone1, ContactsIntentHandler.PHONE);
                return true;
            }
        });

        phone2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                actionHandler(event, phone2, ContactsIntentHandler.PHONE);
                return true;
            }
        });
    }

    private void actionHandler(MotionEvent event, TextView contact, int intent_type) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                changeTexture(contact, 1);
                break;
            case MotionEvent.ACTION_UP:
                changeTexture(contact, 0);
                new ContactsIntentHandler(contact.getText().toString(), intent_type).start();
                break;
        }
    }

    private void changeTexture(TextView txt, int mode) {
        if (mode == 1) {
            txt.setTextColor(Color.BLACK);
            return;
        }
        txt.setTextColor(Color.WHITE);
    }
}
