package com.tiagofarinha.inmezzoapp.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    public static int getAge(String birthday){
        Calendar cal = Calendar.getInstance();

        int curr_day = cal.get(Calendar.DAY_OF_MONTH);
        int curr_month = cal.get(Calendar.MONTH) + 1;
        int curr_year = cal.get(Calendar.YEAR);

        String[] birth = birthday.split("/");

        int day = Integer.parseInt(birth[0]);
        int month = Integer.parseInt(birth[1]);
        int year = Integer.parseInt(birth[2]);

        int before = 0;

        if (curr_month < month || (curr_month == month && curr_day < day))
            before = 1;

        return curr_year - year - before;
    }

    public static String getCurrentDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        Date date = new Date();

        return sdf.format(date) + "h";
    }

}
