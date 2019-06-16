package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class User implements Adaptable {

    public static final int USER = 0, ADMIN = 1, COORD = 2;

    private String user_name, user_birthday, user_voice, user_email, user_pic;
    private int user_phone, user_mode;

    public User() {
    }

    public User(String user_name, String user_birthday, String user_voice, int user_phone, String user_email, String user_pic, int user_mode) {
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_voice = user_voice;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_pic = user_pic;
        this.user_mode = user_mode;
    }

    public String getUser_name() {
        return user_name;
    }

    public String getUser_birthday() {
        return user_birthday;
    }

    public String getUser_voice() {
        return user_voice;
    }

    public String getUser_email() {
        return user_email;
    }

    public String getUser_pic() {
        return user_pic;
    }

    public int getUser_phone() {
        return user_phone;
    }

    public int getUser_mode() {
        return user_mode;
    }

    public String toString() {
        return user_name + user_birthday + user_voice + user_phone + user_email + user_mode;
    }

    @Override
    public boolean equals(Object obj) {
        return user_phone == ((User) obj).getUser_phone();
    }
}
