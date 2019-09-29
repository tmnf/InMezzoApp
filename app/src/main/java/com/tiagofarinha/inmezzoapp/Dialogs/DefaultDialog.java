package com.tiagofarinha.inmezzoapp.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Window;

import androidx.annotation.NonNull;

import com.tiagofarinha.inmezzoapp.R;

public class DefaultDialog extends Dialog implements DialogInterface.OnClickListener {

    public DefaultDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.default_dialog);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }
}
