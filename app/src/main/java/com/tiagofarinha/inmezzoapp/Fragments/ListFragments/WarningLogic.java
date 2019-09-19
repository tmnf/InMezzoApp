package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Adapter.WarningsAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Warning;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class WarningLogic extends DefaultListFragment {

    private DatabaseReference warning_ref;

    public WarningLogic() {
        super(R.string.warning_title);
    }

    @Override
    protected void inflateList() {
        adapter = new WarningsAdapter(ResourceLoader.getInstance().getWarnings(), R.layout.warning_row);

        listView.setAdapter(adapter);

        addListner();
    }

    private void addListner() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Warning warn = (Warning) ResourceLoader.getInstance().getWarnings().get(i);

                if (warn.getLink() != null)
                    if (!warn.getLink().isEmpty())
                        openLink(warn.getLink());
            }
        });

        if (MainMethods.getInstance().isOp())
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                    showDialog((Warning) ResourceLoader.getInstance().getWarnings().get(i));
                    return true;
                }
            });
    }

    private void showDialog(final Warning warning) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        findKeyAndDelete(warning);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMethods.getInstance().getContext());
        builder.setMessage("Deseja eliminar este aviso?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

    private void delete(String key) {
        warning_ref.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Aviso Removido com Sucesso!");
            }
        });
    }

    private void findKeyAndDelete(final Warning warning) {
        warning_ref = FirebaseDatabase.getInstance().getReference().child("warnings");

        warning_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren())
                    if (x.getValue(Warning.class).equals(warning)) {
                        delete(x.getKey());
                        return;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void openLink(String link) {
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(link));
        startActivity(i);
    }
}
