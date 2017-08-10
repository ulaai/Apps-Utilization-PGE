package com.example.uli2.userprofilemgmt;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;

public class VisitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        
        LineChart lChart = (LineChart) findViewById(R.id.line1chart);
        float mult = 100;
        String[] mResult = new String[] { "High", "Low" };
        int[] mValues = new int[] {92, 8};
        int sumValues=0;
        int[] mPercent = new int[mValues.length];

        MakeLineChart(lChart, mResult, mValues);

    }

    private void MakeLineChart(LineChart lChart, String[] mResult, int[] mValues) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 2; i++) {
            entries.add(new Entry(i, mValues[i]));
        }
        LineDataSet lineDataSet;

        lChart.setDrawGridBackground(false);

        //animate chart
        lChart.animateY(2000);

        if (lChart.getData() != null &&
                lChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet)lChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(entries);
            lChart.getData().notifyDataChanged();
            lChart.notifyDataSetChanged();
        } else {
            // create a dataset and give it a type
            lineDataSet = new LineDataSet(entries, "Utilization");

            lineDataSet.setDrawIcons(false);

            // set the line to be drawn like this "- - - - - -"
            lineDataSet.enableDashedLine(10f, 5f, 0f);
            lineDataSet.enableDashedHighlightLine(10f, 5f, 0f);
            lineDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
            lineDataSet.setCircleColor(ColorTemplate.getHoloBlue());
            lineDataSet.setLineWidth(1f);
            lineDataSet.setCircleRadius(3f);
            lineDataSet.setDrawCircleHole(false);
            lineDataSet.setValueTextSize(9f);
            lineDataSet.setDrawFilled(true);
            lineDataSet.setFormLineWidth(1f);
            lineDataSet.setFormLineDashEffect(new DashPathEffect(new float[]{10f, 5f}, 0f));
            lineDataSet.setFormSize(15.f);

            if (Utils.getSDKInt() >= 18) {
                // fill drawable only supported on api level 18 and above
                Drawable drawable = ContextCompat.getDrawable(this, R.drawable.fade_red);
                lineDataSet.setFillDrawable(drawable);
            }
            else {
                lineDataSet.setFillColor(Color.BLACK);
            }
            ArrayList<ILineDataSet> dataSets = new ArrayList<ILineDataSet>();
            dataSets.add(lineDataSet); // add the datasets

            // create a data object with the datasets
            LineData data = new LineData(dataSets);

            // set data
            lChart.setData(data);
        }

    }

}
