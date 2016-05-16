package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.Collections;

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
    protected void onDestroy() {
        super.onDestroy();
        myDbHelper.close();
    }

    @Override
    protected void onResume() {
        super.onResume();
        myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
    }

    @Override
    protected void onPause() {
        super.onPause();
        myDbHelper.close();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logs);
        myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
        recyclerView = (RecyclerView) findViewById(R.id.logsRecyclerView);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        spinner = (Spinner) findViewById(R.id.spinnerLogs);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.periodNames, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManager();
    }

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
        spinner.setPopupBackgroundDrawable(ContextCompat.getDrawable(this, R.color.lightGreen));
        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                orderDialog();
                if(arrayList != null) {
                    Collections.reverse(arrayList);
                    recyclerAdapter.notifyDataSetChanged();
                    return true;
                }
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
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else if(itemText.equals(periodNames[1])){
                    fromDate = myDate.getPreviousDateTime(toDate, "yesterday");
                    toDate = fromDate;
                    toDate = myDate.getPreviousDateTime(toDate, "endDay");
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else if(itemText.equals(periodNames[5])) {
                    arrayList = myDbHelper.getAllData(order);
                    recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                    recyclerView.setAdapter(recyclerAdapter);
                    swipeToDismiss();
                }else {
                    datepickerManager();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner.setSelection(spinnerAdapter.getPosition("Today"));
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

    protected static TextView fromDateTV;
    protected static TextView toDateTV;

    private void datepickerManager() {
        LayoutInflater inflater = LayoutInflater.from(LogsActivity.this);
        final View dialogView = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(LogsActivity.this);
        builder.setView(dialogView);
        fromDateTV = (TextView) dialogView.findViewById(R.id.fromDateTV);
        toDateTV = (TextView) dialogView.findViewById(R.id.toDateTV);
        String text = getResources().getString(R.string.one) + ". " + getResources().getString(R.string.dateTxtView);
        fromDateTV.setText(text);
        text = getResources().getString(R.string.two) + ". " + getResources().getString(R.string.dateTxtView);
        toDateTV.setText(text);
        fromDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker();
                Bundle bundle = new Bundle();
                bundle.putBoolean("which", true);
                datePicker.setArguments(bundle);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });

        toDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePicker datePicker = new DatePicker();
                Bundle bundle = new Bundle();
                bundle.putBoolean("which", false);
                datePicker.setArguments(bundle);
                datePicker.show(getFragmentManager(), "datePicker");
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fromDate = fromDateTV.getText().toString();
                toDate = toDateTV.getText().toString();
                arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                recyclerAdapter = new RecyclerAdapter(arrayList, LogsActivity.this);
                recyclerView.setAdapter(recyclerAdapter);
                swipeToDismiss();
                dialog.dismiss();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                spinner.setSelection(spinnerAdapter.getPosition("Today"));
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    public static class DatePicker extends DialogFragment implements android.app.DatePickerDialog.OnDateSetListener{

        private boolean which;

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            DateTime dateTime = new DateTime();
            int year = dateTime.getYear();
            int month = dateTime.getMonthOfYear();
            int day = dateTime.getDayOfMonth();

            Bundle bundle = getArguments();
            which = bundle.getBoolean("which");
            return new android.app.DatePickerDialog(getActivity(), this, year, month - 1, day);
        }

        @Override
        public void onDateSet(android.widget.DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            String date;
            String month = String.valueOf(monthOfYear + 1);
            String day = String.valueOf(dayOfMonth);

            if(month.length() == 1) {
                month = "0" + month;
            }

            if(day.length() == 1) {
                day = "0" + day;
            }

            if(which) {
                date  = year + "-" + month + "-" + day + " 00:00:00";
                fromDateTV.setText(date);
            }else {
                date = year + "-" + month + "-" + day + " 23:59:59";
                toDateTV.setText(date);
            }
        }
    }

}
