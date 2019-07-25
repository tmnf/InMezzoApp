package com.tiagofarinha.inmezzoapp.Configurations;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;

public class MusicConfig extends Fragment {

    private Music music;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_add, container, false);

        music = (Music) getArguments().getSerializable("music");

        TextView tittle, name, artist;

        tittle = view.findViewById(R.id.music_add_title);
        name = view.findViewById(R.id.music_name);
        artist = view.findViewById(R.id.music_artist);

        Button addButton, removeButton;

        addButton = view.findViewById(R.id.music_button);
        removeButton = view.findViewById(R.id.music_delete);

        //===========================================================\\

        tittle.setText("Editar Música");
        name.setText(music.getName());
        artist.setText(music.getArtist());

        addButton.setText(R.string.update_button);
        removeButton.setVisibility(View.VISIBLE);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateMusic();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeMusic();
            }
        });

        return view;
    }

    private void removeMusic() {

    }

    private void updateMusic() {

    }
}
