package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity implements View.OnLongClickListener{

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

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    private void spinnerManager() {
        spinner.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                LogsOrderDialog dialog = new LogsOrderDialog();
                dialog.show(getFragmentManager(), "Dialog");
                order = dialog.getOption();
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
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[1])){
                    fromDate = myDate.getPreviousDateTime(toDate, "day");
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    arrayList = myDbHelper.getSpecificData(order, fromDate, toDate);
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
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
                org.elsys.valiolucho.businessmonefy.DatePickerDialog.which = true;
                org.elsys.valiolucho.businessmonefy.DatePickerDialog dp = new org.elsys.valiolucho.businessmonefy.DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");
            }
        });

        toDateET.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                org.elsys.valiolucho.businessmonefy.DatePickerDialog.which = true;
                org.elsys.valiolucho.businessmonefy.DatePickerDialog dp = new org.elsys.valiolucho.businessmonefy.DatePickerDialog();
                dp.setCancelable(true);
                dp.show(getFragmentManager(), "DatePicker");
            }
        });
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                fromDate = fromDateET.getText().toString();
                toDate = toDateET.getText().toString();
                dialog.dismiss();
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

    @Override
    public boolean onLongClick(View v) {
        return false;
    }

}
