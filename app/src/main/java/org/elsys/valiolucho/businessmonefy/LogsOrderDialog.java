package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import java.util.ArrayList;

public class LogsOrderDialog extends DialogFragment {

    private String option = "DESC";

    public String getOption() {
        return option;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.chooseOrder)
        .setSingleChoiceItems(R.array.orderOptions, -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    option = "ASC";
                }else {
                    option = "DESC";
                }
            }
        });
        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        return dialog;
    }
}
