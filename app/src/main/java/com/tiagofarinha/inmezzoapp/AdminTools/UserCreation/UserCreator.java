package com.tiagofarinha.inmezzoapp.AdminTools.UserCreation;

public class UserCreator extends Thread {

    /* Handles User Creation */

    private String email, pass, name, birth, voice, phone, mode;

    public UserCreator(String email, String pass, String name, String birth, String voice, String phone, String mode) {
        this.email = email;
        this.pass = pass;
        this.name = name;
        this.birth = birth;
        this.voice = voice;
        this.phone = phone;
        this.mode = mode;
    }

    public void run() {
        try {
            ClientImporter.createClient(email, pass, name, birth, voice, Integer.parseInt(phone), Integer.parseInt(mode));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
