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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class MusicConfig extends Fragment {

    private Music music;
    private String key;
    private DatabaseReference portfolio_ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_add, container, false);

        music = (Music) getArguments().getSerializable("music");
        findKeyFor();

        final TextView tittle, name, artist;

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
                updateMusic(name.getText().toString(), artist.getText().toString());
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

    private void findKeyFor() {
        portfolio_ref = FirebaseDatabase.getInstance().getReference().child("portfolio");

        portfolio_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    if (x.getValue(Music.class).equals(music)) {
                        key = x.getKey();
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void removeMusic() {
        portfolio_ref.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage(music.getName() + " de " + music.getArtist() + " apagada com sucesso!");
                MainMethods.getInstance().getContext().onBackPressed();
            }
        });
    }

    private void updateMusic(String name, String artist) {
        portfolio_ref.child(key).setValue(new Music(name, artist)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Música Atualizada com Sucesso!");
                MainMethods.getInstance().getContext().onBackPressed();
            }
        });
    }

}
