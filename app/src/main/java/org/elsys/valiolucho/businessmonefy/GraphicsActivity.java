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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

        spinner = (Spinner) findViewById(R.id.spinnerGraphs);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.periodNames, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] periodNames = getResources().getStringArray(R.array.periodNames);
                String itemText = parent.getItemAtPosition(position).toString();
                MyDate myDate = new MyDate();
                toDate = myDate.getCurrentDateTime();
                myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
                if(itemText.equals(periodNames[0])) {
                    ChartsSwipeAdapter chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[1])) {

                }else if(itemText.equals(periodNames[2])) {

                }else if(itemText.equals(periodNames[3])) {

                }else if(itemText.equals(periodNames[4])) {

                }else if(itemText.equals(periodNames[5])) {

                }else{
                    arrayList = myDbHelper.getAllData("ASC");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setAdapter(spinnerAdapter);
    }

}
