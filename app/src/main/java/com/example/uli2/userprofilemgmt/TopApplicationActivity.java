package com.example.uli2.userprofilemgmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class TopApplicationActivity extends AppCompatActivity implements AsyncResponse {
    HorizontalBarChart hbChart;
    List<List<String>> MonthlyTopApp;
    String[] mResult;
    int[] mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_application);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        hbChart = (HorizontalBarChart) findViewById(R.id.hb1chart);

        if(MonthlyTopApp == null) {
            Singleton.getInstance().setDelegate(this);
            Singleton.getInstance().getTopMonthlyApplication();
        }

    }

    @Override
    public void processFinish(String output) {
        MonthlyTopApp = Singleton.getInstance().hashMap.get("MTA");

        if(mResult == null) {
            mResult = new String[MonthlyTopApp.get(0).size()];
            for(int i = 0; i < MonthlyTopApp.get(0).size(); i++) {
                String a = MonthlyTopApp.get(0).get(i);
                if(a.length() > 15) {
                    String anew = a.substring(a.lastIndexOf(" ") + 1);
                    int k = a.lastIndexOf(" ");
                    char[] aInChars = a.toCharArray();
                    aInChars[k] = '\n';
                    a = String.valueOf(aInChars);
                }
                mResult[i] = a;
            }

        }

        if(mValues == null) {
            mValues = new int[MonthlyTopApp.get(1).size()];
            for(int i = 0; i < MonthlyTopApp.get(1).size(); i++) {
                int a = Integer.valueOf(MonthlyTopApp.get(1).get(i));
                mValues[i] = a;
            }

        }

        MakeHorizontalBarChart(hbChart, mResult, mValues);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void MakeHorizontalBarChart(HorizontalBarChart hbChart, String[] mResult, int[]
            mValues) {
        ArrayList<String> labels = new ArrayList<>();
        for(int i = 0; i < mResult.length; i++) {
            String a = mResult[i];
            labels.add(a);
        }


        hbChart.setDrawBarShadow(false);
        hbChart.setDrawValueAboveBar(true);
        hbChart.getDescription().setEnabled(false);
        hbChart.setPinchZoom(false);
        hbChart.setDrawGridBackground(false);


        XAxis xl = hbChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setLabelCount(mResult.length);
        // (mResult));
//        CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
//        xl.setValueFormatter(xaxisFormatter);
        xl.setValueFormatter(new IndexAxisValueFormatter(labels));
        xl.setGranularity(1);

        YAxis yl = hbChart.getAxisLeft();
        yl.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yl.setDrawGridLines(false);
        yl.setEnabled(false);
        yl.setAxisMinimum(0f);

        YAxis yr = hbChart.getAxisRight();
        yr.setPosition(YAxis.YAxisLabelPosition.INSIDE_CHART);
        yr.setDrawGridLines(false);
        yr.setAxisMinimum(0f);

        ArrayList<BarEntry> yVals1 = new ArrayList<BarEntry>();
        for (int i = 0; i < mValues.length; i++) {
            yVals1.add(new BarEntry(i, mValues[i]));
        }

        BarDataSet set1;
        set1 = new BarDataSet(yVals1, "DataSet 1");
        set1.setColors(ColorTemplate.MATERIAL_COLORS);
        ArrayList<IBarDataSet> dataSets = new ArrayList<IBarDataSet>();
        dataSets.add(set1);
        BarData data = new BarData(dataSets);
        data.setValueTextSize(10f);
        data.setBarWidth(.9f);
        hbChart.setData(data);
        hbChart.getLegend().setEnabled(false);
        hbChart.setFitBars(true);
        hbChart.animateY(2500);
        hbChart.setDoubleTapToZoomEnabled(false);

    }



    private class CategoryBarChartXaxisFormatter implements IAxisValueFormatter {
        private ArrayList<String> mValues;

        public CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return mValues.get((int)value);
        }
    }


}
