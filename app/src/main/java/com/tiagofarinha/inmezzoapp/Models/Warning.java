package com.tiagofarinha.inmezzoapp.Models;

import androidx.annotation.Nullable;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Warning implements Adaptable {

    private String text, title, pub_date;
    private User user;

    private boolean important;

    public Warning() {
    }

    public Warning(String title, String text, String pub_date, User user, boolean is_important) {
        this.title = title;
        this.text = text;
        this.pub_date = pub_date;
        this.user = user;
        this.important = is_important;
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

    public boolean isImportant() {
        return important;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Warning w = (Warning) obj;

        return text.equals(w.getText()) && title.equals(w.getTitle()) && pub_date.equals(w.getPub_date());
    }
}
