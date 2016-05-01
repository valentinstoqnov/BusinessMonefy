package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.elsys.valiolucho.calculator.CalcActivity;

public class EditRecordDialog extends DialogFragment{

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.edit_dialog, null);
        final TextView nameTV = (TextView) view.findViewById(R.id.editTextName);
        final TextView descriptionTV = (TextView) view.findViewById(R.id.editTextDescription);
        final TextView moneyTV = (TextView) view.findViewById(R.id.editTextPrice);

        builder.setView(view)
                // Add action buttons
                .setPositiveButton(R.string.saveED, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = nameTV.getText().toString();
                        String description = descriptionTV.getText().toString();
                        int money = Integer.parseInt(moneyTV.getText().toString());
                        if (money == 0) {
                            Toast.makeText(getActivity(), "Money can not be 0", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                        Transaction transaction = new Transaction(name, description, money);
                        transaction.setDate();
                        DataBaseHelper myDbHelper = new DataBaseHelper(null);//REPAIR THIS SHIT !!!!!
                        SQLiteDatabase db = myDbHelper.getWritableDatabase();
                        myDbHelper.insertData(transaction, db);
                        myDbHelper.close();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        return builder.create();
    }
}
