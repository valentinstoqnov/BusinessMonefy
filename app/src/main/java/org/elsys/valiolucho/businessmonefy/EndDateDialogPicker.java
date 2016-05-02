package org.elsys.valiolucho.businessmonefy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import org.joda.time.DateTime;

public class EndDateDialogPicker extends DialogFragment {
    EndDateSettings dateSettings;

    public void setDateSettings(EndDateSettings dateSettings) {
        this.dateSettings = dateSettings;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        DatePickerDialog dialog =  new DatePickerDialog(getActivity(), dateSettings, year, month, day);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setCancelable(true);
        dialog.setTitle("To date:");
        return dialog;
    }
}
