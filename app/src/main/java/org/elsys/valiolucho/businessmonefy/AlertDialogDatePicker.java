package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class AlertDialogDatePicker extends DialogFragment {

    TextView fromDateTV;
    TextView toDateTV;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        fromDateTV = (TextView) view.findViewById(R.id.fromDateTV);
        toDateTV = (TextView) view.findViewById(R.id.toDateTV);
        String text = getResources().getString(R.string.one) + ". " + getResources().getString(R.string.dateTxtView);
        fromDateTV.setText(text);
        text = getResources().getString(R.string.two) + ". " + getResources().getString(R.string.dateTxtView);
        toDateTV.setText(text);
        fromDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.which = true;
                DatePickerDialog dp = new DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");
            }
        });

        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.which = true;
                DatePickerDialog dp = new DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");
            }
        });

        builder.setView(view);
        builder.setTitle("Choose two dates");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

}
