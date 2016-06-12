package org.elsys.valiolucho.businessmonefy;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.joda.time.DateTime;

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
    private ChartsSwipeAdapter chartsSwipeAdapter;

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
        setContentView(R.layout.activity_graphics);
        myDbHelper = DataBaseHelper.getInstance(getApplicationContext());
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        spinner = (Spinner) findViewById(R.id.spinnerGraphs);
        spinnerAdapter = ArrayAdapter.createFromResource(this, R.array.periodNames, android.R.layout.simple_spinner_item);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerManager();
    }

    private void spinnerManager() {
        spinner.setPopupBackgroundDrawable(ContextCompat.getDrawable(this, R.color.lightGreen));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String[] periodNames = getResources().getStringArray(R.array.periodNames);
                String itemText = parent.getItemAtPosition(position).toString();
                MyDate myDate = new MyDate();
                toDate = myDate.getCurrentDateTime();
                String period;
                if(itemText.equals(periodNames[0])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "today");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "today";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[1])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "yesterday");
                    toDate = fromDate;
                    toDate = myDate.getPreviousDateTime(toDate, "endDay");
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "yesterday";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[2])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "week");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "week";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[3])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "month");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "month";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[4])) {
                    fromDate = myDate.getPreviousDateTime(toDate, "year");
                    toDate = myDate.getCurrentDateTime();
                    arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                    period = "year";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else if(itemText.equals(periodNames[5])) {
                    arrayList = myDbHelper.getAllData(ORDER);
                    period = "all";
                    chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, period, getApplicationContext());
                    viewPager.setAdapter(chartsSwipeAdapter);
                }else{
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

    protected static TextView fromDateTV;
    protected static TextView toDateTV;

    private void datepickerManager() {
        LayoutInflater inflater = LayoutInflater.from(this);
        final View dialogView = inflater.inflate(R.layout.date_picker_dialog_layout, null);
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
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
                arrayList = myDbHelper.getSpecificData(ORDER, fromDate, toDate);
                chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager(), arrayList, "", getApplicationContext());
                viewPager.setAdapter(chartsSwipeAdapter);
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
            return new android.app.DatePickerDialog(getActivity(), this, year, month, day);
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
