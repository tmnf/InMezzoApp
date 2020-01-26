package com.tiagofarinha.inmezzoapp.Dialogs;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.widget.EditText;

import com.tiagofarinha.inmezzoapp.MainLogic.MainMethods;

import java.util.Calendar;

public class CalendarAux {

    public static void openDatePicker(EditText dateDisplay, EditText hourDisplay) {

        Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR),
                month = c.get(Calendar.MONTH),
                day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(MainMethods.getInstance().getContext(), (view, mYear, mMonth, dayOfMonth) -> {

            String date = dayOfMonth + "/" + (mMonth + 1) + "/" + mYear;
            dateDisplay.setText(date);

            openTimePicker(hourDisplay);
        }, year, month, day);

        datePickerDialog.show();
    }

    public static void openTimePicker(EditText hourDisplay) {
        Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY),
                mMinute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(MainMethods.getInstance().getContext(), (view, hourOfDay, minute) -> {
            String hour = hourOfDay + ":" + minute;
            hourDisplay.setText(hour);
        }, mHour, mMinute, true);

        timePickerDialog.show();
    }

}
