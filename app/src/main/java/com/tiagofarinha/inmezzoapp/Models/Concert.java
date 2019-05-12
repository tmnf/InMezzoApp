package com.tiagofarinha.inmezzoapp.Models;

public class Concert {

    private String date, local, descr;

    public Concert(String date, String local, String descr) {
        this.date = date;
        this.local = local;
        this.descr = descr;
    }

    public Concert() {
    }

    public String getDate() {
        return date;
    }

    public String getLocal() {
        return local;
    }

    public String getDescr() {
        return descr;
    }
}
