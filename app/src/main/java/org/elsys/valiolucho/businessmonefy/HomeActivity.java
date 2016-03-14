package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

public class HomeActivity extends AppCompatActivity {

    private Button showGraphicsButton;
    private Button showLogsButton;

    private ImageButton plusImageButton;
    private ImageButton minusImageButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickButtonListeners();
        onClickImageButtonsListeners();
    }

    //make SHOW GRAPHICS and SHOW LOGS buttons, initialization and set onClickListener
    private void onClickButtonListeners() {
        showGraphicsButton = (Button) findViewById(R.id.buttonShowGraphics);
        showGraphicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphicsActivityIntent = new Intent(HomeActivity.this, GraphicsActivity.class);
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
                Intent plusActivityIntent = new Intent(HomeActivity.this, PlusActivity.class);
                startActivity(plusActivityIntent);
            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonMinus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent minusActivityIntent = new Intent(HomeActivity.this, MinusActivity.class);
                startActivity(minusActivityIntent);
            }
        });
    }

}
