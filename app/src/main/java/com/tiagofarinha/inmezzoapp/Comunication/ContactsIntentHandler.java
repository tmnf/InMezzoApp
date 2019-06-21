package com.tiagofarinha.inmezzoapp.Comunication;

import android.content.Intent;
import android.net.Uri;

import com.tiagofarinha.inmezzoapp.Fragments.ContactsLogic;
import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;

public class ContactsIntentHandler extends Thread {

    /* Handles Contact Intents */

    public final static int PHONE = 0, EMAIL = 1, FACEBOOK = 2, INSTAGRAM = 3, YOUTUBE = 4;

    private int mode;
    private String contact;

    public ContactsIntentHandler(String contact, int mode) {
        this.contact = contact;
        this.mode = mode;
    }

    public void run() {
        switch (mode) {
            case PHONE:
                initPhoneCall();
                break;
            case EMAIL:
                initEmail();
                break;
            case FACEBOOK:
                initFacebook();
                break;
            case INSTAGRAM:
                initInstagram();
                break;
            case YOUTUBE:
                initYoutube();
                break;
        }

        interrupt();
    }


    private void initInstagram() {
        Uri uri;

        try {
            MainMethods.getInstance().getContext().getPackageManager().getPackageInfo("com.instagram.android", 0);
            uri = Uri.parse("http://www.instagram.com/_u/" + ContactsLogic.INSTAGRAM_URL);
        } catch (Exception e) {
            uri = Uri.parse("http://instagram.com/" + ContactsLogic.INSTAGRAM_URL);
        }

        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void initFacebook() {
        Uri uri;

        try {
            MainMethods.getInstance().getContext().getPackageManager().getPackageInfo("com.facebook.katana", 0);
            uri = Uri.parse("fb://facewebmodal/f?href=" + ContactsLogic.FACEBOOK_URL);
        } catch (Exception e) {
            uri = Uri.parse(ContactsLogic.FACEBOOK_URL);
        }

        startActivity(new Intent(Intent.ACTION_VIEW, uri));
    }

    private void initYoutube() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(ContactsLogic.YOUTUBE_URL));
        startActivity(intent);
    }

    private void initPhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", contact, null));
        startActivity(intent);
    }

    private void initEmail() {
        String[] email = {contact};

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, email);
        intent.setType("message/rfc822");

        startActivity(Intent.createChooser(intent, "Escolha o Seu Cliente de Email"));
    }

    private void startActivity(Intent it) {
        MainMethods.getInstance().startActivity(it);
    }


}
