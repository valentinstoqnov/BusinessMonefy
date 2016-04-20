package org.elsys.valiolucho.drawer;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class MyBarChart extends Drawer{

    BarChart barChart;
    ArrayList<BarEntry> entries;
    ArrayList<String> labels;
    String description;
    String label;

    public MyBarChart(ArrayList<BarEntry> entries, ArrayList<String> labels, String description, String label) {
        this.entries = entries;
        this.labels = labels;
        this.description = description;
        this.label = label;
    }

    @Override
    public void draw() {
        BarDataSet dataSet = new BarDataSet(entries, label);
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        BarData data = new BarData(labels, dataSet);
        barChart.setData(data);
        barChart.setDescription(description);
        barChart.animateXY(2000, 2000);
        barChart.invalidate();
    }

    @Override
    public void setColor(int color) {

    }

    @Override
    public void setColors(int[] colors) {

    }

    @Override
    public void setDescription(String description) {
        barChart.setDescription(description);
    }

    @Override
    public void animateX(int duration) {
        barChart.animateX(duration);
    }

    @Override
    public void animateY(int duration) {
        barChart.animateY(duration);
    }

    @Override
    public void animateXY(int xDuration, int yDuration) {
        barChart.animateXY(xDuration, yDuration);
    }

    public BarChart getBarChart() {
        return barChart;
    }
}
