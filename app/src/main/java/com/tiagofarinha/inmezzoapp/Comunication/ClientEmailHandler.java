package com.tiagofarinha.inmezzoapp.Comunication;

import android.content.Intent;

import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;

public class ClientEmailHandler extends Thread {

    /* Handles Client Email Service */

    private static final String[] EMAIL = {"inmezzo.orfeao@gmail.com"};

    private String subject, body;

    public ClientEmailHandler(String subject, String body) {
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void run() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        intent.setType("message/rfc822");
        MainActivity.getInstance().startActivity(Intent.createChooser(intent, "Escolha o seu cliente de email"));

        interrupt();
    }


}
