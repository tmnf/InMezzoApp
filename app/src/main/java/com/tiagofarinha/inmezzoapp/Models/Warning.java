package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Warning implements Adaptable {

    private String text, title, pub_date;
    private User user;

    public Warning() {
    }

    public Warning(String title, String text, String pub_date, User user) {
        this.title = title;
        this.text = text;
        this.pub_date = pub_date;
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public String getTitle() {
        return title;
    }

    public String getPub_date() {
        return pub_date;
    }

    public User getUser() {
        return user;
    }
}
