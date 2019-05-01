package com.tiagofarinha.inmezzoapp.Email;

import android.content.Intent;

import com.tiagofarinha.inmezzoapp.MainLogic.MainActivity;

public class EmailHandler extends Thread {

    private static final String[] EMAIL = {"tiagomnf1999@gmail.com"};

    private String subject, body;

    public EmailHandler(String subject, String body){
        this.subject = subject;
        this.body = body;
    }

    @Override
    public void run(){
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, EMAIL);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);

        intent.setType("message/rfc822");
        MainActivity.getInstance().startActivity(Intent.createChooser(intent, "Escolha o seu cliente de email"));
    }


}
