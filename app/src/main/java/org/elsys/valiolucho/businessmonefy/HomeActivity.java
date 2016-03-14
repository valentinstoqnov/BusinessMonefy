package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class HomeActivity extends AppCompatActivity {

    private static Button showGraphicsButton;
    private static Button showLogsButton;

    private static ImageButton plusImageButton;
    private static ImageButton minusImageButton;

    private PopupWindow plusPopUpWindow;
    private LayoutInflater plusLayoutInFlatter;
    private LinearLayout plusLayout;

    private PopupWindow minusPopUpWindow;
    private LayoutInflater minusLayoutInFlatter;
    private LinearLayout minusLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        onClickButtonListeners();
    }

    //make SHOW GRAPHICS and SHOW LOGS buttons, initialization and set onClickListener
    public void onClickButtonListeners(){
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

        plusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        plusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                plusLayoutInFlatter = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) plusLayoutInFlatter.inflate(R.layout.plus_layout, null);
                plusLayout = (LinearLayout) findViewById(R.id.plusLinearLayout);

                //400,400 must found formula !
                plusPopUpWindow = new PopupWindow(container,400,400);
                plusPopUpWindow.showAtLocation(plusLayout, Gravity.NO_GRAVITY,500,500);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        plusPopUpWindow.dismiss();
                        return true;
                    }
                });
            }
        });

        minusImageButton = (ImageButton) findViewById(R.id.imageButtonPlus);
        minusImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                minusLayoutInFlatter = (LayoutInflater) getApplicationContext().getSystemService(LAYOUT_INFLATER_SERVICE);
                ViewGroup container = (ViewGroup) minusLayoutInFlatter.inflate(R.layout.minus_layout, null);
                minusLayout = (LinearLayout) findViewById(R.id.minusLinearLayout);

                //400,400 must found formula !
                minusPopUpWindow = new PopupWindow(container,400,400);
                minusPopUpWindow.showAtLocation(minusLayout,Gravity.NO_GRAVITY,500,500);

                container.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        minusPopUpWindow.dismiss();
                        return true;
                    }
                });
            }
        });
    }

}
