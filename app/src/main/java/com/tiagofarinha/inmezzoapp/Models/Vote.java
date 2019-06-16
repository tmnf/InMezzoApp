package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Vote implements Adaptable {

    private Ensaio ensaio;
    private User user;

    private int value;

    public Vote() {
    }

    public Vote(Ensaio ensaio, User user, int value) {
        this.ensaio = ensaio;
        this.user = user;
        this.value = value;
    }

    public Ensaio getEnsaio() {
        return ensaio;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }
}
