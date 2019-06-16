package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Warning implements Adaptable {

    private String text, pub_date;
    private User user;

    public Warning() {
    }

    public Warning(String text, String pub_date, User user) {
        this.text = text;
        this.pub_date = pub_date;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getPub_date() {
        return pub_date;
    }

    public User getUser() {
        return user;
    }
}
