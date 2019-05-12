package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;

public class AddMusic extends Fragment {

    private EditText name, artist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_add, container, false);

        return view;
    }

    private void getComps(View view) {
        name = view.findViewById(R.id.music_name);
        artist = view.findViewById(R.id.music_artist);

        Button add = view.findViewById(R.id.music_button);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addInfo();
            }
        });
    }

    private void addInfo() {
        Music music = new Music(name.getText().toString(), artist.getText().toString());
        DatabaseReference port_ref = FirebaseDatabase.getInstance().getReference().child("portfolio");
        port_ref.push().setValue(music);
    }
}
