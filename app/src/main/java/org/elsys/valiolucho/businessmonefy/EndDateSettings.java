package org.elsys.valiolucho.businessmonefy;

import android.app.DatePickerDialog;
import android.widget.DatePicker;

public class EndDateSettings implements DatePickerDialog.OnDateSetListener{

    DateHolder holder;

    public void setHolder(DateHolder holder) {
        this.holder = holder;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = new MyDate().getPreviousDateTime(Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth)
                + " 23:59:59", "today");
        holder.setEndDate(date);
    }
}

