package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickButtonListeners();
    }

    //make SHOW GRAPHICS and SHOW LOGS buttons, initialization and set onClickListener
    private static Button showGraphicsButton;
    private static Button showLogsButton;
    private static ImageButton plusImageButton;
    private static ImageButton minusImageButton;

    public void onClickButtonListeners(){
        showGraphicsButton = (Button) findViewById(R.id.buttonShowGraphics);
        showGraphicsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent graphicsActivityIntent = new Intent(HomeActivity.this, GraphicsActivity.class);﻿
                startActivity(graphicsActivityIntent);
            }
        });

        showLogsButton = (Button) findViewById(R.id.buttonShowLogs);
        showLogsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logsActivityIntent = new Intent(HomeActivity.this, LogsActivity.class);﻿
                startActivity(logsActivityIntent);
            }
        });

        plusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        plusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
