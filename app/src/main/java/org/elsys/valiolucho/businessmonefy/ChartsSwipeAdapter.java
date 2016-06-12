package org.elsys.valiolucho.businessmonefy;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import java.util.ArrayList;

public class ChartsSwipeAdapter extends FragmentStatePagerAdapter {

    private DataProcess dataProcess;
    private Context context;
    public static String TEXTVIEW_STR = "tvStr";

    public ChartsSwipeAdapter(FragmentManager fm, ArrayList<Transaction> data, String period, Context context) {
        super(fm);
        this.context = context;
        dataProcess = new DataProcess(data, period);
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fr;
        Bundle bundle;
        DataProcess newData;
        switch (position) {
            case 0:
                fr = new LineChartFragment();
                bundle = new Bundle();
                bundle.putString(TEXTVIEW_STR, context.getString(R.string.outAndIncoms));
                bundle.putFloatArray("values", dataProcess.getValues());
                bundle.putStringArray("labels", dataProcess.getLabels());
                bundle.putString("period", dataProcess.getPeriod());
                fr.setArguments(bundle);
                break;
            case 1:
                fr = new LineChartFragment();
                newData = dataProcess.getFilteredData("pos");
                bundle = new Bundle();
                bundle.putString(TEXTVIEW_STR, context.getString(R.string.incoms));
                bundle.putFloatArray("values", newData.getValues());
                bundle.putStringArray("labels", newData.getLabels());
                bundle.putString("period", newData.getPeriod());
                fr.setArguments(bundle);
                break;
            default:
                fr = new LineChartFragment();
                newData = dataProcess.getFilteredData("neg");
                bundle = new Bundle();
                bundle.putString(TEXTVIEW_STR, context.getString(R.string.outcoms));
                bundle.putFloatArray("values", newData.getValues());
                bundle.putStringArray("labels", newData.getLabels());
                bundle.putString("period", newData.getPeriod());
                fr.setArguments(bundle);
                break;
        }
        return fr;
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "ALL";
            case 1:
                return "Incomings";
            default:
                return "Outcomings";
        }
    }
}
