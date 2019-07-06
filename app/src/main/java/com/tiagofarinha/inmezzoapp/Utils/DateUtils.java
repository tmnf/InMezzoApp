package com.tiagofarinha.inmezzoapp.Utils;

import com.tiagofarinha.inmezzoapp.Models.MezzoDate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    private static MezzoDate getCurrDate() {
        Calendar c = Calendar.getInstance();

        return parseToDate(c.get(Calendar.DAY_OF_MONTH) + "/" +
                (c.get(Calendar.MONTH) + 1) + "/" +
                c.get(Calendar.YEAR));
    }

    public static MezzoDate parseToDate(String dateInText) {
        return new MezzoDate(dateInText);
    }

    public static int getAge(String birthday) {
        MezzoDate date = parseToDate(birthday);
        MezzoDate curr = getCurrDate();

        int before = 0;

        if (curr.getMonth() < date.getMonth() || (curr.getMonth() == date.getMonth() && curr.getDay() < date.getDay()))
            before = 1;

        return curr.getYear() - date.getYear() - before;
    }

    public static boolean hasPassed(String date1) {
        try {
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy");

            Date date = format.parse(date1);
            Date currDate = getCurrDate().toDate();

            return currDate.getTime() > date.getTime();
        } catch (ParseException e) {
            return false;
        }
    }

    public static String getCurrentDateInText() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date date = new Date();

        return sdf.format(date) + "h";
    }

}
