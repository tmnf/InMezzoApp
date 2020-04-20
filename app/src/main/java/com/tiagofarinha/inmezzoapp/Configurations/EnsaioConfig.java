package com.tiagofarinha.inmezzoapp.Configurations;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Ensaio;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class EnsaioConfig extends Fragment {

    private Ensaio ensaio;
    private String key;
    private DatabaseReference ensaios_ref;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.ensaio_add, container, false);

        ensaio = (Ensaio) getArguments().getSerializable("ensaio");
        findKeyFor();

        final TextView tittle, date, hour, descr;

        tittle = view.findViewById(R.id.ensaio_add_title);
        date = view.findViewById(R.id.ensaio_date);
        hour = view.findViewById(R.id.ensaio_hour);
        descr = view.findViewById(R.id.ensaio_descr);

        Button add, delete;

        add = view.findViewById(R.id.ensaio_button);
        delete = view.findViewById(R.id.ensaio_delete);

        add.setText(R.string.update_button);
        delete.setVisibility(View.VISIBLE);

        tittle.setText("Editar Ensaio");

        String[] dateText = ensaio.getDate().split(",");
        date.setText(dateText[0]);
        hour.setText(dateText[1]);

        descr.setText(ensaio.getDescr());

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(date.getText().toString() + "," + hour.getText().toString(), descr.getText().toString());
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove();
            }
        });

        return view;
    }

    private void update(String datetime, String descr) {
        ResourceLoader.getInstance().deleteVotes(key);
        ensaios_ref.child(key).setValue(new Ensaio(datetime, descr)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Ensaio Atualizado com Sucesso!");
                MainMethods.getInstance().getContext().onBackPressed();
            }
        });
    }

    private void remove() {
        ResourceLoader.getInstance().deleteVotes(key);
        ensaios_ref.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Ensaio Removido com Sucesso!");
                MainMethods.getInstance().getContext().onBackPressed();
            }
        });
    }


    private void findKeyFor() {
        ensaios_ref = FirebaseDatabase.getInstance().getReference().child("ensaios");

        ensaios_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren())
                    if (x.getValue(Ensaio.class).equals(ensaio)) {
                        key = x.getKey();
                        return;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void showDialog(final String datetime, final String descr) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        update(datetime, descr);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMethods.getInstance().getContext());
        builder.setMessage("Ao realizar esta operação todos os votos serão perdidos. Deseja continuar?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

}
