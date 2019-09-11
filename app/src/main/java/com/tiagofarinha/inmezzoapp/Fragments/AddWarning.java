package com.tiagofarinha.inmezzoapp.Fragments;

import android.net.Uri;
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
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
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
        final EditText text, title, link;

        text = view.findViewById(R.id.warning_add_text);
        title = view.findViewById(R.id.warning_add_title_field);
        link = view.findViewById(R.id.warning_add_link);

        Button warningButton = view.findViewById(R.id.warning_button);

        warningButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (text.getText().toString().isEmpty() || title.getText().toString().isEmpty()) {
                    Utils.showMessage("Campos vazio!");
                    return;
                }

                if (!link.getText().toString().isEmpty()) {
                    try {
                        Uri.parse(link.getText().toString());
                    } catch (Exception e) {
                        return;
                    }
                }

                addWarning(title.getText().toString(), text.getText().toString(), link.getText().toString());
            }
        });
    }

    private void addWarning(String title, String text, String link) {
        Warning warning = new Warning(title, text, DateUtils.getCurrentDateInText(), MainMethods.getInstance().getAuxUser(), link);

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
