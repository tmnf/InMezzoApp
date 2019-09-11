package com.tiagofarinha.inmezzoapp.Models;

import androidx.annotation.Nullable;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Warning implements Adaptable {

    private String text, title, pub_date, link;
    private User user;

    public Warning() {
    }

    public Warning(String title, String text, String pub_date, User user, String link) {
        this.title = title;
        this.text = text;
        this.pub_date = pub_date;
        this.user = user;
        this.link = link;
    }

    public String getLink() {
        return link;
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

    @Override
    public boolean equals(@Nullable Object obj) {
        Warning w = (Warning) obj;

        return text.equals(w.getText()) && title.equals(w.getTitle()) && pub_date.equals(w.getPub_date());
    }
}
