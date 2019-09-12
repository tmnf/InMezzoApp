package com.tiagofarinha.inmezzoapp.Models;

import androidx.annotation.Nullable;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;

public class Post implements Adaptable {

    private String post_text, url, date_pub;

    private User owner;

    public Post(String post_text, String url, User owner) {
        this.post_text = post_text;
        this.url = url;
        this.date_pub = DateUtils.getCurrentDateInText();
        this.owner = owner;
    }

    public Post() {
    }

    public String getPost_text() {
        return post_text;
    }

    public String getUrl() {
        return url;
    }

    public String getDate_pub() {
        return date_pub;
    }

    public User getOwner() {
        return owner;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        Post p = (Post) obj;

        return p.getOwner().equals(owner) && p.getPost_text().equals(post_text) && p.getDate_pub().equals(date_pub) && p.getUrl().equals(url);
    }
}
