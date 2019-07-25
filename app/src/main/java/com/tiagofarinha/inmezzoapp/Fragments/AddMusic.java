package com.tiagofarinha.inmezzoapp.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.Models.Music;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class AddMusic extends Fragment {

    private EditText name, artist;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.music_add, container, false);

        getComps(view);

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
        if (!(name.getText().toString().isEmpty() && artist.getText().toString().isEmpty())) {
            Music music = new Music(name.getText().toString(), artist.getText().toString());
            DatabaseReference port_ref = FirebaseDatabase.getInstance().getReference().child("portfolio");

            Utils.showMessage("A adicionar música...");
            port_ref.push().setValue(music).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    MenuUtils.filterMenuItem(R.id.menu_portfolio);
                    Utils.showMessage("Música Adicionada Com Sucesso");
                }
            });

        } else {
            Utils.showMessage("Campos vazios!");
        }
    }
}
