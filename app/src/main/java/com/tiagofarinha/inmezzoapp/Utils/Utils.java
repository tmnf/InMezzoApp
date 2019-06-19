package com.tiagofarinha.inmezzoapp.Utils;

import android.widget.Toast;

import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;

public class Utils {

    public static void showMessage(String message) {
        Toast.makeText(MainMethods.getInstance().getContext(), message, Toast.LENGTH_LONG).show();
    }

}
