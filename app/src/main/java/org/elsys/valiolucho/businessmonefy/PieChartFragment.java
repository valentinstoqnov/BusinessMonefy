package org.elsys.valiolucho.businessmonefy;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import org.elsys.valiolucho.drawer.MyPieChart;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PieChartFragment extends Fragment {

    //private PieChart pieChart;

    private MyPieChart myChart;

    public PieChartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        Bundle bundle = getArguments();
        String message = Integer.toString(bundle.getInt("Chart"));

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(4f, 0));
        entries.add(new Entry(8f, 1));
        entries.add(new Entry(6f, 2));
        entries.add(new Entry(12f, 3));
        entries.add(new Entry(18f, 4));
        entries.add(new Entry(9f, 5));
       // PieDataSet dataset = new PieDataSet(entries, "# of Calls");
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");
        View view = inflater.inflate(R.layout.fragment_pie_chart,container,false);
        myChart = new MyPieChart((PieChart) view.findViewById(R.id.pieChart),entries,labels, "Some Label");
        myChart.setDescription("My Chart: " + message);
        myChart.animateY(2000);
        myChart.setColors(ColorTemplate.VORDIPLOM_COLORS);
        myChart.draw();
        return view;
    }

}
