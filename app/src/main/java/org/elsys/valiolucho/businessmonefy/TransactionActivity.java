package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import java.math.BigDecimal;
import java.util.List;

public class TransactionActivity extends AppCompatActivity {
    private RelativeLayout relativeLayout;
    private EditText trNameET, trDescriptionET, trMoneyET;
    private TextView currencyET;
    private boolean isMinus = false;
    private DataBaseHelper myDb;
    public static final String CURRENCY_PREFS = "CurrencyPrefs";
    public static final String CURRENCY = "Currency";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Bundle extras = getIntent().getExtras();
        if(extras == null) {
            isMinus = false;
        }else{
            isMinus = true;
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (isMinus && toolbar != null) {
            toolbar.setBackgroundColor(Color.parseColor("#D32F2F"));
        }
        setSupportActionBar(toolbar);
        relativeLayout = (RelativeLayout) findViewById(R.id.trRelativeLayout);
        if(isMinus){
            relativeLayout.setBackgroundColor(Color.parseColor("#FFCDD2"));
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Color.parseColor("#B71C1C"));
        }
        trNameET = (EditText) findViewById(R.id.trNameET);
        trDescriptionET = (EditText) findViewById(R.id.trDescriptionET);
        trMoneyET = (EditText) findViewById(R.id.trMoneyET);
        currencyET = (TextView) findViewById(R.id.trCurrencyTV);

        SharedPreferences prefs = getSharedPreferences(CURRENCY_PREFS, Context.MODE_PRIVATE);
        String currency = prefs.getString(CURRENCY, null);
        if(currency != null) {
            currencyET.setText(currency);
        }else{
            String defaultCurrency = getString(R.string.defaultCurrency);
            currencyET.setText(defaultCurrency);
            storeCurrencyPrefs(defaultCurrency);
        }
        FloatingActionButton floatingActionButton = (FloatingActionButton) findViewById(R.id.fab);
        if(floatingActionButton != null) {
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent calculatorIntent = new Intent();
                    calculatorIntent.setAction(Intent.ACTION_MAIN);
                    calculatorIntent.addCategory(Intent.CATEGORY_APP_CALCULATOR);
                    PackageManager packageManager = getPackageManager();
                    List activities = packageManager.queryIntentActivities(calculatorIntent , PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) {
                        startActivity(calculatorIntent);
                    }else {
                        Toast.makeText(getApplicationContext(), "To perform this action you should have calculator", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        onSwipeListener();
    }

    private void storeCurrencyPrefs(String currency) {
        SharedPreferences prefs = getSharedPreferences(CURRENCY_PREFS, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(CURRENCY, currency);
        editor.apply();
    }

    private void onSwipeListener() {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeLeft() {
                String name = trNameET.getText().toString();
                if (name.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Name field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String description = trDescriptionET.getText().toString();
                if (description.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Description field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                String moneyStr = trMoneyET.getText().toString();
                if (moneyStr.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Money field is empty", Toast.LENGTH_SHORT).show();
                    return;
                }
                BigDecimal money = new BigDecimal(moneyStr);
                if(money.compareTo(BigDecimal.ZERO) == 0) {
                    Toast.makeText(getApplicationContext(), "Sum can not be 0", Toast.LENGTH_LONG).show();
                    return;
                }
                if (isMinus && money.compareTo(BigDecimal.ZERO) == 1) {
                    money = money.negate();
                }else if(!isMinus && money.compareTo(BigDecimal.ZERO) == -1) {
                    money = money.negate();
                }
                Transaction transaction = new Transaction(name, description, money);
                transaction.setDate();
                myDb = DataBaseHelper.getInstance(getApplicationContext());
                myDb.insertData(transaction);
                Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

}
