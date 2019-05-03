package com.tiagofarinha.inmezzoapp.Fragments;

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

import com.tiagofarinha.inmezzoapp.Comunication.IntentHandler;
import com.tiagofarinha.inmezzoapp.R;

public class ContactsLogic extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.contacts_fragment, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {

        final TextView email, phone1, phone2;

        email = view.findViewById(R.id.email_texto);
        phone1 = view.findViewById(R.id.contacto1_texto);
        phone2 = view.findViewById(R.id.contacto2_texto);

        email.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeTexture(email, 1);
                        break;
                    case MotionEvent.ACTION_UP:
                        changeTexture(email, 0);
                        new IntentHandler(email.getText().toString(), IntentHandler.EMAIL).start();
                        break;
                }
                return true;
            }
        });

        phone1.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeTexture(phone1, 1);
                        break;
                    case MotionEvent.ACTION_UP:
                        changeTexture(phone1, 0);
                        new IntentHandler(phone1.getText().toString(), IntentHandler.PHONE).start();
                        break;
                }
                return true;
            }
        });

        phone2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        changeTexture(phone2, 1);
                        break;
                    case MotionEvent.ACTION_UP:
                        changeTexture(phone2, 0);
                        new IntentHandler(phone2.getText().toString(), IntentHandler.PHONE).start();
                        break;
                }
                return true;
            }
        });
    }

    private void changeTexture(TextView txt, int mode) {
        if (mode == 1) {
            txt.setTextColor(Color.BLACK);
            return;
        }
        txt.setTextColor(Color.WHITE);
    }
}
