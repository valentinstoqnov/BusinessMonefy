package org.elsys.valiolucho.businessmonefy;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import org.joda.time.DateTime;

public class DatePickerDialog extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

    TextView fromDate;
    TextView toDate;
    public static boolean which;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        DateTime dateTime = new DateTime();
        int year = dateTime.getYear();
        int month = dateTime.getMonthOfYear();
        int day = dateTime.getDayOfMonth();
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        fromDate = (TextView) view.findViewById(R.id.fromDateTV);
        Log.d("TEXT === ", fromDate.getText().toString());
        toDate = (TextView) view.findViewById(R.id.toDateTV);
        return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        Log.d("TEXT === ", fromDate.getText().toString());
        String date = year + "-" + monthOfYear + "-" + dayOfMonth + " 00:00:00";
        if(which) {
           toDate.setText(date);
        }else {
            fromDate.setText(date);
        }
    }
}
