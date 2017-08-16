package com.example.uli2.userprofilemgmt;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class VisitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        String value = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            value = extras.getString("sourceFragment");
            Log.d("myTag", value);
        }
        String title = value + " Visitor";
        getSupportActionBar().setTitle(title);

        LineChart lChart = (LineChart) findViewById(R.id.line1chart);
        float mult = 100;
        String[] mResult = new String[] { "High", "Low" };
        int[] mValues = new int[] {92, 8, 16, 20, 22};
        int sumValues=0;
        int[] mPercent = new int[mValues.length];

        MakeLineChart(lChart, mResult, mValues);

    }

    private void MakeLineChart(LineChart lChart, String[] mResult, int[] mValues) {
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < mValues.length; i++) {
            entries.add(new Entry(i, mValues[i]));
        }

        List<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");

        LineDataSet lineDataSet;

        lChart.setClickable(false);
        lChart.setPinchZoom(false);
        lChart.setDragEnabled(false);
        lChart.setTouchEnabled(true);
        lChart.setScaleEnabled(false);
        lChart.setDrawGridBackground(false);
        lChart.getLegend().setEnabled(false);
        lChart.setDoubleTapToZoomEnabled(false);
        lChart.getDescription().setEnabled(false);
        lChart.animateY(3000);

        XAxis xAxis = lChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setEnabled(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawLabels(true);
        xAxis.setTextColor(getResources().getColor(R.color.colorHigh));
        xAxis.setDrawGridLines(false);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setLabelCount(5);
        xAxis.setGranularity(1);

        YAxis axisLeft = lChart.getAxisLeft();
        axisLeft.removeAllLimitLines();
        axisLeft.setDrawGridLines(true);
        axisLeft.setGridColor(getResources().getColor(R.color.colorLow));
        axisLeft.setDrawAxisLine(false);
        axisLeft.setLabelCount(4, false);
        axisLeft.setDrawLabels(true);

        lChart.getAxisRight().setEnabled(false);
        lChart.setExtraOffsets(2f,1f,1f,2f);
        lChart.setHighlightPerDragEnabled(true);



        //animate chart
        lChart.animateY(2000);

        if (lChart.getData() != null &&
                lChart.getData().getDataSetCount() > 0) {
            lineDataSet = (LineDataSet)lChart.getData().getDataSetByIndex(0);
            lineDataSet.setValues(entries);
            lineDataSet.setLineWidth(2f);
            lineDataSet.setCircleColor(R.color.colorHigh);
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
            lineDataSet.setLineWidth(2f);
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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


}
