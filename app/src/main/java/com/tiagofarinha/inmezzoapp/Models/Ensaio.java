package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Ensaio implements Adaptable {

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
}
