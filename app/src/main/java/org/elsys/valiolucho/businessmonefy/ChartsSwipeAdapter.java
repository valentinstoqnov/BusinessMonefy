package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

import java.util.ArrayList;

public class ChartsSwipeAdapter extends FragmentStatePagerAdapter {

    private DataProcess dataProcess;

    public ChartsSwipeAdapter(FragmentManager fm, ArrayList<Transaction> data, String period) {
        super(fm);
        dataProcess = new DataProcess(data, period);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr;
        Bundle bundle;
        switch (position) {
            case 0:
                fr = new LineChartFragment();
                bundle = new Bundle();
                bundle.putFloatArray("values", dataProcess.getValues());
                bundle.putStringArray("labels", dataProcess.getLabels());
                bundle.putString("period", dataProcess.getPeriod());
                fr.setArguments(bundle);
                break;
            case 1:
                fr = new PieChartFragment();
                bundle = new Bundle();
                bundle.putFloatArray("values", dataProcess.getValues());
                bundle.putStringArray("labels", dataProcess.getLabels());
                bundle.putString("period", dataProcess.getPeriod());
                fr.setArguments(bundle);
                /*bundle = new Bundle();
                bundle.putFloat("incomings", dataProcess.getIncomings().floatValue());
                bundle.putFloatArray("values", dataProcess.getMinusVals());
                bundle.putStringArray("labels", dataProcess.getLabels());
                bundle.putString("period", dataProcess.getPeriod());
                fr.setArguments(bundle);*/
                break;
            default:
                fr = new LineChartFragment();
                bundle = new Bundle();
                bundle.putFloatArray("values", dataProcess.getValues());
                bundle.putStringArray("labels", dataProcess.getLabels());
                bundle.putString("period", dataProcess.getPeriod());
                fr.setArguments(bundle);
                break;
        }
        return fr;
    }

    @Override
    public int getCount() {
        return 2;
    }
}
