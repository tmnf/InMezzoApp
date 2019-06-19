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

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;
import com.tiagofarinha.inmezzoapp.Utils.MenuUtils;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class AddWarning extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.warning_add, container, false);

        getComps(view);

        return view;
    }

    private void getComps(View view) {
        final EditText text = view.findViewById(R.id.warning_add_text);
        Button warningButton = view.findViewById(R.id.warning_button);

        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty()) {
                    Utils.showMessage("Campo vazio!");
                    return;
                }
                addWarning(text.getText().toString());
            }
        });
    }

    private void addWarning(String text) {
        Warning warning = new Warning(text, DateUtils.getCurrentDate(), MainActivity.getInstance().getAuxUser());

        DatabaseReference warningRef = FirebaseDatabase.getInstance().getReference().child("warnings");
        warningRef.push().setValue(warning).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Adicionado com Sucesso!");
                MenuUtils.filterMenuItem(R.id.menu_warnings);
            }
        });
    }
}