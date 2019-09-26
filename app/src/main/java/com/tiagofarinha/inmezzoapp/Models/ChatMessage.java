package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;
import com.tiagofarinha.inmezzoapp.Utils.DateUtils;

public class ChatMessage implements Adaptable {

    private User user;
    private String msg, pub_date;

    public ChatMessage() {
    }

    public ChatMessage(User user, String msg) {
        this.user = user;
        this.msg = msg;
        this.pub_date = DateUtils.getCurrentDateInText();
    }

    public User getUser() {
        return user;
    }

    public String getMsg() {
        return msg;
    }

    public String getPub_date() {
        return pub_date;
    }
}
