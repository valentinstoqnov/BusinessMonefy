package org.elsys.valiolucho.businessmonefy;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

import org.joda.time.DateTime;

public class DateSettings implements DatePickerDialog.OnDateSetListener{

    private String date;

    public String getDate() {
        return date;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        DateTime dateTime = new DateTime(year, monthOfYear, dayOfMonth,  0, 0);
    }
}
