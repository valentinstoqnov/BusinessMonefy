package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import java.math.BigDecimal;

public class HomeActivity extends AppCompatActivity {

    private Button showGraphicsButton;
    private Button showLogsButton;

    private ImageButton plusImageButton;
    private ImageButton minusImageButton;

    private TextView incomeTV;
    private TextView outcomeTV;
    private TextView totalTV;

    @Override
    protected void onResume() {
        super.onResume();
        textViewsManager();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);;
        incomeTV = (TextView) findViewById(R.id.incomeTV);
        outcomeTV = (TextView) findViewById(R.id.outcomeTV);
        totalTV = (TextView) findViewById(R.id.totalTV);
        textViewsManager();
        onClickButtonListeners();
        onClickImageButtonsListeners();
    }

    private void textViewsManager() {
        DataBaseHelper myDb = DataBaseHelper.getInstance(getApplicationContext());
        DataProcess dataProcess = new DataProcess(myDb.getAllData("ASC"));
        myDb.close();
        BigDecimal incomings = dataProcess.getIncomings();
        BigDecimal outcomings = dataProcess.getOutcomings();
        incomeTV.setText(incomings.toPlainString());
        outcomeTV.setText(outcomings.toPlainString());
        BigDecimal total = (((incomings.subtract(outcomings.negate())).setScale(2, BigDecimal.ROUND_HALF_EVEN)).stripTrailingZeros());
        if (total.compareTo(BigDecimal.ZERO) == 1) {
            totalTV.setTextColor(ContextCompat.getColor(this, R.color.colorTrPlus));
        }else if(total.compareTo(BigDecimal.ZERO) == -1) {
            totalTV.setTextColor(ContextCompat.getColor(this, R.color.colorTrMinus));
        }
        totalTV.setText(total.toPlainString());
    }

    //make SHOW GRAPHICS and SHOW LOGS buttons, initialization and set onClickListener
    private void onClickButtonListeners() {
        showGraphicsButton = (Button) findViewById(R.id.buttonShowGraphics);
        showGraphicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent graphicsActivityIntent = new Intent(HomeActivity.this, GraphicsActivity.class);
                startActivity(graphicsActivityIntent);
            }
        });

        showLogsButton = (Button) findViewById(R.id.buttonShowLogs);
        showLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logsActivityIntent = new Intent(HomeActivity.this, LogsActivity.class);
                startActivity(logsActivityIntent);
            }
        });
    }

    private void onClickImageButtonsListeners() {
        plusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        plusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent plusActivityIntent = new Intent(HomeActivity.this, CalcActivity.class);
                startActivity(plusActivityIntent);
            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonMinus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent minusActivityIntent = new Intent(HomeActivity.this, CalcActivity.class);
                minusActivityIntent.putExtra("minus", true);
                startActivity(minusActivityIntent);
            }
        });
    }

}
