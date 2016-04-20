package org.elsys.valiolucho.drawer;

import android.util.EventLogTags;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MyPieChart extends Drawer{

    private PieChart pieChart;
    private String description;
    private String label;

    private PieDataSet dataSet;
    private PieData data;

    public MyPieChart(PieChart pieChart, ArrayList<Entry> entries, ArrayList<String> labels, String label) {
        this.pieChart = pieChart;
        this.dataSet = new PieDataSet(entries, label);
        this.data = new PieData(labels, dataSet);
    }

    @Override
    public void draw() {
        pieChart.setDescription(description);
        pieChart.setData(data);
    }

    @Override
    public void setColor(int color) {
        dataSet.setColor(color);
    }

    @Override
    public void setColors(int[] colors) {
        dataSet.setColors(colors);
    }

    @Override
    public void setDescription(String description) {
        pieChart.setDescription(description);
    }

    @Override
    public void animateX(int num) {
        pieChart.animateX(num);
    }

    @Override
    public void animateY(int num) {
        pieChart.animateY(num);
    }

    @Override
    public void animateXY(int xDuration, int yDuration) {
        pieChart.animateXY(xDuration, yDuration);
    }

    public PieChart getPieChart() {
        return pieChart;
    }
}
