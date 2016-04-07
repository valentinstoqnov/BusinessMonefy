package org.elsys.valiolucho.businessmonefy;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import org.elsys.valiolucho.calculator.CalcActivity;

public class PlusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plus);
        Intent calcActivityIntent = new Intent(PlusActivity.this, CalcActivity.class);
        startActivity(calcActivityIntent);
    }

}
