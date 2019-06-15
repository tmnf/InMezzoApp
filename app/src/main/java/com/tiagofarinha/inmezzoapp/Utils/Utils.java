package com.tiagofarinha.inmezzoapp.Utils;

import android.widget.Toast;

import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;

public class Utils {

    public static void showMessage(String message) {
        Toast.makeText(MainActivity.getInstance(), message, Toast.LENGTH_LONG).show();
    }

}
