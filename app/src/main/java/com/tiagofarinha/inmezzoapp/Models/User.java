package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class User implements Adaptable {

    public static final int ADMIN = 1, COORD = 2, SUSP = 3;

    private String user_name, user_birthday, user_voice, user_pic, user_bio;
    private int user_phone, user_mode, strikes, votes;

    public User() {
    }

    public User(String user_name, String user_birthday, String user_voice, int user_phone, String user_pic, int user_mode, int votes) {
        this.user_name = user_name;
        this.user_birthday = user_birthday;
        this.user_voice = user_voice;
        this.user_phone = user_phone;
        this.user_pic = user_pic;
        this.user_mode = user_mode;
        this.user_bio = "";
        this.strikes = 0;
        this.votes = 0;
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

    public String getUser_bio() {
        return user_bio;
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

    public int getStrikes() {
        return strikes;
    }

    public int getVotes() {
        return votes;
    }

    public String toString() {
        return user_name + user_birthday + user_voice + user_phone + user_mode;
    }

    @Override
    public boolean equals(Object obj) {
        return user_phone == ((User) obj).getUser_phone();
    }

    public void addStrike(int value) {
        strikes += value;
    }

    public int checkBehavior() {
        int res = 1;

        if (strikes >= 2 && strikes <= 4)
            res = 2;
        if (strikes > 4 && strikes <= 6)
            res = 3;
        if (strikes > 6 && strikes <= 8)
            res = 4;
        if (strikes > 8)
            res = 5;

        return res;
    }
}
