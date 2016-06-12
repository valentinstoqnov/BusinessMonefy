package org.elsys.valiolucho.businessmonefy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class LineChartFragment extends Fragment{
    private float[] values;
    private String[] labels;
    private String period;
    private String text;

    public LineChartFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        text = bundle.getString(ChartsSwipeAdapter.TEXTVIEW_STR);
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
        TextView textView = (TextView) view.findViewById(R.id.graphicsTV);
        textView.setText(text);
        LineChart lineChart = (LineChart)view.findViewById(R.id.lineChart);
        lineChart.setData(data);
        lineChart.setDescription("");
        lineChart.getAxisLeft().setDrawLabels(false);
        lineChart.getAxisRight().setDrawLabels(false);
        lineChart.getLegend().setEnabled(false);
        lineChart.animateY(2000);
        lineChart.setAutoScaleMinMaxEnabled(true);
        lineChart.setScaleEnabled(false);
        lineChart.setPinchZoom(false);
        lineChart.setDoubleTapToZoomEnabled(false);
        return view;
    }
}
