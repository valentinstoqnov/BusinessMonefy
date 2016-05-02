package org.elsys.valiolucho.businessmonefy;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Transaction> arrayList = new ArrayList<>();

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private DataBaseHelper myDbHelper;//= DataBaseHelper.getInstance(this);
    private SQLiteDatabase db;
    private Cursor cursor;
    private String order = "DESC";

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
                String toDate = myDate.getCurrentDateTime();
                String fromDate;
                myDbHelper = new DataBaseHelper(LogsActivity.this);
                db = myDbHelper.getReadableDatabase();
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }else if(itemText.equals(periodNames[1])){
                    fromDate = myDate.getPreviousDateTime(toDate, "day");
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }else if(itemText.equals(periodNames[5])) {
                    cursor = myDbHelper.getAllData(db, order);
                }else {
                    DateHolder holder = new DateHolder();
                    StartDateDialogPicker dialogPicker = new StartDateDialogPicker();
                    dialogPicker.setDateHolder(holder);
                    dialogPicker.show(LogsActivity.this.getFragmentManager(), "date_picker");
                    fromDate = holder.getStartDate();
                    toDate = holder.getEndDate();
                    cursor = myDbHelper.getSpecificData(db, order, fromDate, toDate);
                }
                db.close();
                myDbHelper.close();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                cursor = myDbHelper.getAllData(db, order);
                db.close();
                myDbHelper.close();
            }
        });
        spinner.setAdapter(spinnerAdapter);
        if(cursor != null) {
            cursor.moveToFirst();
            do {
                Transaction transaction = new Transaction(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
                transaction.setDate(cursor.getString(3));
                arrayList.add(transaction);
            } while (cursor.moveToNext());
            cursor.close();
        }


        recyclerAdapter = new RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
