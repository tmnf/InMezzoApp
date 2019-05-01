package com.tiagofarinha.inmezzoapp.Adapter;

import com.tiagofarinha.inmezzoapp.AdminTools.User;

public class Post {

    private String post_text, url, date_pub;

    private User owner;

    public Post(String post_text, String url, String date_pub, User owner){
        this.post_text = post_text;
        this.url = url;
        this.date_pub = date_pub;
        this.owner = owner;
    }

    public Post(){}

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
}
