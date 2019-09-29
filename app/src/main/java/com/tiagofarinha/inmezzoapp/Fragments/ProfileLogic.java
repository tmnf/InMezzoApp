package com.tiagofarinha.inmezzoapp.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Configurations.ProfileConfig;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.User;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;
import com.tiagofarinha.inmezzoapp.Utils.LoginUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class ProfileLogic extends Fragment {

    private ImageView pic;
    private TextView name, age, voice, settings, bio;

    private Button mod_strikes;

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
        mod_strikes = view.findViewById(R.id.change_strike);

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

        String age_text, voice_text;

        age_text = "Idade: " + DateUtils.getAge(user.getUser_birthday()) + " Anos";
        voice_text = "Voz: " + user.getUser_voice();

        age.setText(age_text);
        voice.setText(voice_text);

        bio.setText(user.getUser_bio());

        addConfigListener();

        checkModButton(user);
    }

    private void checkModButton(final User user) {
        if (!MainMethods.getInstance().isOp()) {
            mod_strikes.setVisibility(View.GONE);
            return;
        }

        mod_strikes.setVisibility(View.VISIBLE);
        mod_strikes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openPopup(user);
            }
        });
    }

    private void openPopup(final User user) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        modStrike(user, 1);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEUTRAL:
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        modStrike(user, -1);
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMethods.getInstance().getContext());
        builder.setMessage("Faltas: " + user.getStrikes()).setPositiveButton("Adicionar Falta", dialogClickListener)
                .setNegativeButton("Remover Falta", dialogClickListener).setNeutralButton("Cancelar", dialogClickListener).show();
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
                        MainMethods.getInstance().changeFrag(new ProfileConfig(), R.id.menu_perfil, true);
                        break;
                }
                return true;
            }
        });
    }

    private void modStrike(final User user, final int value) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                User aux = null;
                DatabaseReference ref = null;
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    aux = x.getValue(User.class);

                    if (user.equals(aux)) {
                        ref = x.child("strikes").getRef();
                        break;
                    }
                }

                if (aux.getStrikes() == 0 && value == -1) {
                    Utils.showMessage("Limite de Faltas Mínimo Atingido");
                    return;
                }

                ref.setValue(aux.getStrikes() + value);
                Utils.showMessage("Faltas Modificadas com Sucesso Para Utilizador: " + user.getUser_name());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
