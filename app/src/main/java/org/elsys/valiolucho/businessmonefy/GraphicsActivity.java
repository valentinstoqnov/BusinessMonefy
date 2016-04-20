package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

public class GraphicsActivity extends FragmentActivity {

    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graphics);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        ChartsSwipeAdapter chartsSwipeAdapter = new ChartsSwipeAdapter(getSupportFragmentManager());
        viewPager.setAdapter(chartsSwipeAdapter);
    }

}
