package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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
    private static final int CALCULATOR_REQUEST = 1;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CALCULATOR_REQUEST) {
            if (resultCode == RESULT_OK) {
                trMoneyET.setText(data.getDataString());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
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
                        startActivityForResult(calculatorIntent, CALCULATOR_REQUEST);
                    }else {
                        Toast.makeText(getApplicationContext(), "To perform this action you should hava calculator", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        onSwipeListener();
    }

    private void onSwipeListener() {
        relativeLayout.setOnTouchListener(new OnSwipeTouchListener(getApplicationContext()){
            @Override
            public void onSwipeLeft() {
                String name = trNameET.getText().toString();
                String description = trDescriptionET.getText().toString();
                BigDecimal money = new BigDecimal(trMoneyET.getText().toString());
                if(money.compareTo(BigDecimal.ZERO) == 0) {
                    Toast.makeText(getApplicationContext(), "Sum can not be 0", Toast.LENGTH_LONG).show();
                    return;
                }
                if (isMinus && money.compareTo(BigDecimal.ZERO) == 1) {
                    money = money.negate();
                }else if(!isMinus && money.compareTo(BigDecimal.ZERO) == 1) {
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
