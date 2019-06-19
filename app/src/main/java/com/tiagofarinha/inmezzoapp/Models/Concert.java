package com.tiagofarinha.inmezzoapp.Models;

import com.tiagofarinha.inmezzoapp.Interfaces.Adaptable;

public class Concert implements Adaptable {

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

    @Override
    public boolean equals(Object obj) {
        Concert concert = (Concert) obj;

        return concert.getDate().equals(date) && concert.getDescr().equals(descr) && concert.getLocal().equals(local);
    }
}
