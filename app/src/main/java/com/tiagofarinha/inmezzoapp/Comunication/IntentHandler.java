package com.tiagofarinha.inmezzoapp.Comunication;

import android.content.Intent;
import android.net.Uri;

import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;

public class IntentHandler extends Thread {

    public static int PHONE = 0, EMAIL = 1;

    private int mode;
    private String contact;

    public IntentHandler(String contact, int mode) {
        this.contact = contact;
        this.mode = mode;
    }

    public void run() {
        if (mode == PHONE) {
            initPhoneCall();
            return;
        }
        initEmail();
    }

    private void initPhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact, null));
        MainActivity.getInstance().startActivity(intent);
    }

    private void initEmail() {
        String[] email = {contact};

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.setType("message/rfc822");

        MainActivity.getInstance().startActivity(Intent.createChooser(intent, "Escolha o Seu Cliente de Email"));
    }


}
