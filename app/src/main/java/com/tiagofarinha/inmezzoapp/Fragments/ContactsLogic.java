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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);

        getComps(view);

        return view;
    }

    @SuppressLint("ClickableViewAccessibility")
    private void getComps(View view) {

        final TextView email, phone1, phone2;

        email = view.findViewById(R.id.email_texto);
        phone1 = view.findViewById(R.id.contacto1_texto);
        phone2 = view.findViewById(R.id.facebook_texto);

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
