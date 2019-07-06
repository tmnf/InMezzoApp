package com.tiagofarinha.inmezzoapp.Models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MezzoDate {

    private int day, month, year;

    public MezzoDate(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public MezzoDate(String date) {
        String[] info = date.split("/");

        this.day = Integer.parseInt(info[0]);
        this.month = Integer.parseInt(info[1]);
        this.year = Integer.parseInt(info[2]);
    }

    public Date toDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        try {
            return sdf.parse(day + "/" + month + "/" + year);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }
}
