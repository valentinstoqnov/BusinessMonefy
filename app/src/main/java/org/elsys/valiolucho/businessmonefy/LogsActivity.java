package org.elsys.valiolucho.businessmonefy;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class LogsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter recyclerAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private ArrayList<Transaction> arrayList = new ArrayList<>();

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private DataBaseHelper myDbHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String order;

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
        myDbHelper = new DataBaseHelper(this);
        db = myDbHelper.getReadableDatabase();

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
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    cursor = myDbHelper.getSpecificData(db, fromDate, toDate, order);
                }else if(itemText.equals(periodNames[1])){
                    fromDate = myDate.getPreviousDateTime(toDate, "day");
                    cursor = myDbHelper.getSpecificData(db, fromDate, toDate, order);
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    cursor = myDbHelper.getSpecificData(db, fromDate, toDate, order);
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    cursor = myDbHelper.getSpecificData(db, fromDate, toDate, order);
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    cursor = myDbHelper.getSpecificData(db, fromDate, toDate, order);
                }else if(itemText.equals(periodNames[5])) {
                    cursor = myDbHelper.getAllData(db, order);
                }else {
                    //dialog with datetime picker
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spinnerAdapter);
        cursor.moveToFirst();
        do{
            Transaction transaction = new Transaction(cursor.getString(0), cursor.getString(1), cursor.getInt(2));
            transaction.setDate(cursor.getString(3));
            arrayList.add(transaction);
        }while (cursor.moveToNext());
        myDbHelper.close();

        recyclerAdapter = new RecyclerAdapter(arrayList, this);
        recyclerView.setAdapter(recyclerAdapter);
    }

}
