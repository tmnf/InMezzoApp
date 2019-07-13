package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Votable;

public class Ensaio implements Votable {

    private String date, descr;

    public Ensaio(String date, String descr) {
        this.date = date;
        this.descr = descr;
    }

    public Ensaio() {
    }

    public String getDate() {
        return date;
    }

    public String getDescr() {
        return descr;
    }

    @Override
    public boolean equals(Object obj) {
        Ensaio ensaio = (Ensaio) obj;

        return ensaio.getDate().equals(date) && ensaio.getDescr().equals(descr);
    }
}
