package com.tiagofarinha.inmezzoapp.Services;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

public class ChildListener implements ChildEventListener {

    private String msg;
    private BackgroundResourceLoader brl;

    private long time = System.currentTimeMillis();

    public ChildListener(String msg, BackgroundResourceLoader brl) {
        this.msg = msg;
        this.brl = brl;
    }

    @Override
    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
        if (brl.isActive() && (System.currentTimeMillis() - time) > 5000)
            brl.sendNotification(msg);
    }

    @Override
    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

    }

    @Override
    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

    }

    @Override
    public void onCancelled(@NonNull DatabaseError databaseError) {

    }
}
