package org.elsys.valiolucho.businessmonefy;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

public class StartDateSettings implements DatePickerDialog.OnDateSetListener{

    Context context;
    DateHolder holder;

    public void setContext(Context context) {
        this.context = context;
    }

    public void setHolder(DateHolder holder) {
        this.holder = holder;
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        String date = new MyDate().getPreviousDateTime(Integer.toString(year) + "-" + Integer.toString(monthOfYear) + "-" + Integer.toString(dayOfMonth)
                + " 0:0:0", "today");
        holder.setStartDate(date);
        EndDateSettings dateSettings = new EndDateSettings();
        dateSettings.setHolder(holder);
        EndDateDialogPicker dialog = new EndDateDialogPicker();
        dialog.setDateSettings(dateSettings);
        dialog.show(((Activity) context).getFragmentManager(), "datePicker");
    }

}
