package org.elsys.valiolucho.businessmonefy;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import org.joda.time.DateTime;

public class DateDialogPicker extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateSettings dateSettings = new DateSettings();
        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        return new DatePickerDialog(getActivity(), dateSettings, year, month, day);
    }
}
