package com.tiagofarinha.inmezzoapp.Utils;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.tiagofarinha.inmezzoapp.Fragments.PollLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;
import com.tiagofarinha.inmezzoapp.Models.User;

public class Utils {

    public static void showMessage(String message) {
        Toast.makeText(MainMethods.getInstance().getContext(), message, Toast.LENGTH_LONG).show();
    }

    public static void getUser(User us, PollLogic logic) {
        FirebaseDatabase.getInstance().getReference().child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot x : dataSnapshot.getChildren()) {
                    User aux = x.getValue(User.class);
                    if (aux.equals(us)) {
                        logic.setUser(x.getRef());
                        logic.setUserInfo((long) x.child("votes").getValue());
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public static void changeVote(DatabaseReference us, long votes) {
        us.getRef().child("votes").setValue(votes);
    }
}
