package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Vote implements Adaptable {

    private String eventKey;
    private User user;

    private int value;

    public Vote() {
    }

    public Vote(String eventKey, User user, int value) {
        this.eventKey = eventKey;
        this.user = user;
        this.value = value;
    }

    public String getEventKey() {
        return eventKey;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }
}
