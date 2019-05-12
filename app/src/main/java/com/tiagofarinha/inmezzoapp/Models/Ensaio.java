package com.tiagofarinha.inmezzoapp.Models;

public class Ensaio {

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
