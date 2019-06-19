package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Vote implements Adaptable {

    private Ensaio ensaio;
    private Concert concert;
    private User user;

    private int value;

    public Vote() {
    }

    public Vote(Adaptable event, User user, int value) {
        if (event instanceof Ensaio)
            this.ensaio = (Ensaio) event;
        else
            this.concert = (Concert) event;

        this.user = user;
        this.value = value;
    }

    public Ensaio getEnsaio() {
        return ensaio;
    }

    public Concert getConcert() {
        return concert;
    }

    public User getUser() {
        return user;
    }

    public int getValue() {
        return value;
    }
}
