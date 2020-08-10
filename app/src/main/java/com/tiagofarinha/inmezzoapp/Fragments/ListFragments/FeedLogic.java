package com.tiagofarinha.inmezzoapp.Fragments.ListFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Adapter.PostAdapter;
import com.tiagofarinha.inmezzoapp.Cache.ResourceLoader;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.Post;
import com.tiagofarinha.inmezzoapp.R;
import com.tiagofarinha.inmezzoapp.Utils.Utils;

public class FeedLogic extends DefaultListFragment {

    private DatabaseReference post_ref;

    public FeedLogic() {
        super(R.string.feed_title);
    }

    @Override
    protected void inflateList() {
        adapter = new PostAdapter(ResourceLoader.getInstance().getPosts(), R.layout.post_row);
        listView.setAdapter(adapter);

        addListener();
    }

    private void addListener() {
        listView.setOnItemLongClickListener((adapterView, view, i, l) -> {
            if (MainMethods.getInstance().isMember())
                checkPermission((Post) ResourceLoader.getInstance().getPosts().get(i));

            return true;
        });
    }

    private void checkPermission(Post post) {
        if (MainMethods.getInstance().getAuxUser().equals(post.getOwner()) || MainMethods.getInstance().isOp())
            showDialog(post);
    }

    private void showDialog(final Post post) {
        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case DialogInterface.BUTTON_POSITIVE:
                        findKeyAndDelete(post);
                        dialog.dismiss();
                        break;
                    case DialogInterface.BUTTON_NEGATIVE:
                        dialog.dismiss();
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainMethods.getInstance().getContext());
        builder.setMessage("Deseja eliminar esta publicação?").setPositiveButton("Sim", dialogClickListener)
                .setNegativeButton("Não", dialogClickListener).show();
    }

    private void delete(String key) {
        post_ref.child(key).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Utils.showMessage("Publicação Removido com Sucesso!");
            }
        });
    }

    private void findKeyAndDelete(final Post post) {
        post_ref = FirebaseDatabase.getInstance().getReference().child("posts");

        post_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren())
                    if (x.getValue(Post.class).equals(post)) {
                        delete(x.getKey());
                        return;
                    }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }
}
