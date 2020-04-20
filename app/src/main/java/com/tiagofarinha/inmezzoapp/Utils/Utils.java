package com.tiagofarinha.inmezzoapp.Utils;

import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;

public class Utils {

    // Shows a Popup Temporary Message
    public static void showMessage(String message) {
        Toast.makeText(MainMethods.getInstance().getContext(), message, Toast.LENGTH_SHORT).show();
    }

    // Updates Votes of A User
    public static void changeVotesOfUser(String userKey, long votes) {
        FirebaseDatabase.getInstance().getReference().child("users").child(userKey).child("votes").setValue(votes);
    }
}
