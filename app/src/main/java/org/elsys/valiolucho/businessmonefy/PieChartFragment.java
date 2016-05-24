package org.elsys.valiolucho.businessmonefy;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.elsys.valiolucho.drawer.MyPieChart;

import java.util.ArrayList;


public class PieChartFragment extends Fragment {

    private PieChart pieChart;
    private float[] values;
    private String[] labels;
    private String period;

    public PieChartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        values = bundle.getFloatArray("values");
        labels = bundle.getStringArray("labels");
        period = bundle.getString("period");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ArrayList<Entry> entries = new ArrayList<>();
        int index = 0;
        for (float value : values) {
            entries.add(new Entry(value, index));
            index++;
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        PieData data = new PieData(labels, dataSet);
        View view = inflater.inflate(R.layout.fragment_pie_chart, container, false);
        pieChart = (PieChart)view.findViewById(R.id.pieChart);
        pieChart.setData(data);
        pieChart.setDescription(period);
        pieChart.animateXY(2000, 2000);
        return view;
    }
}
