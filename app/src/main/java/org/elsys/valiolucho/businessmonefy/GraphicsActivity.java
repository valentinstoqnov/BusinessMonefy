package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

public class GraphicsActivity extends FragmentActivity {

    ViewPager viewPager;

    private Spinner spinner;
    private ArrayAdapter<CharSequence> spinnerAdapter;

    private DataBaseHelper myDbHelper;
    private ArrayList<Transaction> arrayList = new ArrayList<>();
    private String fromDate;
    private String toDate;
    private static final String ORDER = "ASC";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        spinner = (Spinner) findViewById(R.id.spinnerGraphs);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.periodNames, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManager();
    }

    private void spinnerManager() {
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] periodNames = getResources().getStringArray(R.array.periodNames);
                String itemText = parent.getItemAtPosition(position).toString();
                MyDate myDate = new MyDate();
                toDate = myDate.getCurrentDateTime();
                myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
                ChartsSwipeAdapter chartsSwipeAdapter;
                String period;
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "today";
                }else if(itemText.equals(periodNames[1])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "yesterday");
                    toDate = fromDate;
                    toDate = myDate.getPreviousDateTime(toDate, "endDay");
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "yesterday";
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "week";
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "month";
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "year";
                }else if(itemText.equals(periodNames[5])) {
                    arrayList = myDbHelper.getAllData(ORDER);
                    period = "all";
                }else{
                    //date pickers...
                    arrayList = myDbHelper.getAllData(ORDER);
                    period = "all";
                }
                myDbHelper.close();
                chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period);
                viewPager.setAdapter(chartsSwipeAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spinnerAdapter);
    }

}
