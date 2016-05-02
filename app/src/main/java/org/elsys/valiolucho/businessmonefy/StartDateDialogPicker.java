package org.elsys.valiolucho.businessmonefy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import org.joda.time.DateTime;

public class StartDateDialogPicker extends DialogFragment {

    DateHolder holder;

    public void setDateHolder(DateHolder holder) {
        this.holder = holder;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        StartDateSettings dateSettings = new StartDateSettings();
        dateSettings.setHolder(holder);
        dateSettings.setContext(getActivity());
        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DatePickerDialog dialog =  new DatePickerDialog(getActivity(), dateSettings, year, month, day);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("From date:");
        return dialog;
    }
}
