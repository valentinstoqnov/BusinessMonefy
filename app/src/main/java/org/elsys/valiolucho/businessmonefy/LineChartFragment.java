package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LineChartFragment extends Fragment{

    private LineChart lineChart;
    private float[] values;
    private String[] labels;
    private String period;
    
    public LineChartFragment() {}

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
        LineDataSet dataset = new LineDataSet(entries, period);
        
        LineData data = new LineData(labels, dataset);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setDrawCubic(true);
        dataset.setDrawFilled(true);

        View view = inflater.inflate(R.layout.fragment_line_chart, container, false);
        lineChart = (LineChart)view.findViewById(R.id.lineChart);
        lineChart.setData(data);
        lineChart.animateY(2000);
        return view;
    }
}
