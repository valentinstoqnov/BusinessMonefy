package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Transaction> arrayList = new ArrayList<>();

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private DataBaseHelper myDbHelper;
    private String order = "DESC";

    private DateHolder dateHolder;

    private String toDate;
    private String fromDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();

        setContentView(R.layout.activity_logs);
        recyclerView = (RecyclerView) findViewById(R.id.logsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        spinner = (Spinner) findViewById(R.id.spinnerLogs);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.periodNames, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManager();

    }

    /*private void tabsManager() {
        ActionBar actionBar = getActionBar();
        actionBar.setHomeButtonEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        // For each of the sections in the app, add a tab to the action bar.
        for (int i = 0; i < 3; i++) {
            actionBar.addTab(
                    actionBar.newTab()
                            .setTabListener(this));
        }
    }*/

    private void orderDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(LogsActivity.this);
        builder.setTitle(R.string.chooseOrder)
                .setSingleChoiceItems(R.array.orderOptions, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            order = "ASC";
                        }else {
                            order = "DESC";
                        }
                        dialog.dismiss();
                    }
                });
        Dialog dialog = builder.create();
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    private void spinnerManager() {
        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                orderDialog();
                myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
                arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                myDbHelper.close();
                recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                swipeToDismiss();
                return false;
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] periodNames = getResources().getStringArray(R.array.periodNames);
                String itemText = parent.getItemAtPosition(position).toString();
                MyDate myDate = new MyDate();
                toDate = myDate.getCurrentDateTime();
                myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[1])){
                    fromDate = myDate.getPreviousDateTime(toDate, "yesterday");
                    toDate = fromDate;
                    toDate = myDate.getPreviousDateTime(toDate, "endDay");
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[5])) {
                    arrayList = myDbHelper.getAllData(order);
                }else {
                    datepickerManager();
                }
                myDbHelper.close();
                recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                swipeToDismiss();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                arrayList = myDbHelper.getAllData(order);
                myDbHelper.close();
            }
        });
        spinner.setAdapter(spinnerAdapter);
    }

    private void swipeToDismiss() {
        ItemTouchHelper swipeToDismissTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Transaction transaction = arrayList.get(viewHolder.getAdapterPosition());
                arrayList.remove(viewHolder.getAdapterPosition());
                myDbHelper.deleteData(transaction);
                recyclerAdapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }
        });
        swipeToDismissTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void datepickerManager() {
        LayoutInflater inflater = LayoutInflater.from(LogsActivity.this);
        final View dialogView = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(LogsActivity.this);
        builder.setView(dialogView);
        builder.setTitle("Choose two dates");
        final TextView fromDateET;
        final TextView toDateET;
        fromDateET = (TextView) dialogView.findViewById(R.id.fromDateTV);
        toDateET = (TextView) dialogView.findViewById(R.id.toDateTV);
        String text = getResources().getString(R.string.one) + ". " + getResources().getString(R.string.dateTxtView);
        fromDateET.setText(text);
        text = getResources().getString(R.string.two) + ". " + getResources().getString(R.string.dateTxtView);
        toDateET.setText(text);
        fromDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker();
                datePicker.which = true;
                datePicker.show(getFragmentManager(), "datePicker");
                /*org.elsys.valiolucho.businessmonefy.DatePickerDialog.which = true;
                org.elsys.valiolucho.businessmonefy.DatePickerDialog dp = new org.elsys.valiolucho.businessmonefy.DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");*/
            }
        });

        toDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker();
                datePicker.which = false;
                datePicker.show(getFragmentManager(), "datePicker");
                /*org.elsys.valiolucho.businessmonefy.DatePickerDialog.which = true;
                org.elsys.valiolucho.businessmonefy.DatePickerDialog dp = new org.elsys.valiolucho.businessmonefy.DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");*/
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fromDate = fromDateET.getText().toString();
                toDate = toDateET.getText().toString();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
        arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
    }

    public static class DatePicker extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

        private TextView fromDate;
        private TextView toDate;
        public boolean which;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DateTime dateTime = new DateTime();
            int year = dateTime.getYear();
            int month = dateTime.getMonthOfYear();
            int day = dateTime.getDayOfMonth();
            LayoutInflater inflater = getActivity().getLayoutInflater();
            View view = inflater.inflate(R.layout.date_picker_dialog_layout, null);
            fromDate = (TextView) view.findViewById(R.id.fromDateTV);
            toDate = (TextView) view.findViewById(R.id.toDateTV);
            Log.d("sdsad", fromDate.getText().toString());
            Log.d("sdsad", toDate.getText().toString());
            return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date;
            if(which) {
                date  = year + "-" + monthOfYear + "-" + dayOfMonth + " 00:00:00";
                toDate.setText(date);
            }else {
                date = year + "-" + monthOfYear + "-" + dayOfMonth + " 23:59:59";
                fromDate.setText(date);
            }
        }
    }

}
