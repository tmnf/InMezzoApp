package com.tiagofarinha.inmezzoapp.Utils;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.User;

public class Utils {

    public static void showMessage(String message) {
        Toast.makeText(MainMethods.getInstance().getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static int ADD_VOTE = 0, DELETE_VOTE = 1;

    public static void changeVote(User us, int mode) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    User aux = x.getValue(User.class);
                    if (aux.equals(us)) {

                        long votes = (long) x.child("votes").getValue();

                        if (mode == ADD_VOTE)
                            x.getRef().child("votes").setValue(votes + 1);
                        else if (mode == DELETE_VOTE && votes > 0)
                            x.getRef().child("votes").setValue(votes - 1);

                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

}
