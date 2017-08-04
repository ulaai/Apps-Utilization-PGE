package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.uli2.userprofilemgmt.AppRecyclerView.CActivity;
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

public class TopUserActivity extends AppCompatActivity implements AsyncResponse {
    HorizontalBarChart hbChart;
    List<List<String>> MonthlyTopUser;
    String[] mResult;
    int[] mValues;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        hbChart = (HorizontalBarChart) findViewById(R.id.hb1chart);
        if(MonthlyTopUser == null) {
            Singleton.getInstance().setDelegate(this);
            Singleton.getInstance().getTopMonthlyUser();
        }

        Button cbutton = (Button) findViewById(R.id.cbutton);
        cbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent (TopUserActivity.this, CActivity.class);
                startActivity(intent);
            }
        });


    }

    @Override
    public void processFinish(String output) {
        MonthlyTopUser = Singleton.getInstance().hashMap.get("MU");

        if(mResult == null) {
            int mResultSize = MonthlyTopUser.get(0).size();
            mResult = new String[mResultSize];
            for(int i = 0; i < mResultSize; i++) {
                String a = MonthlyTopUser.get(0).get(mResultSize-i-1);
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


        int mValuesSize = MonthlyTopUser.get(1).size();
        if(mValues == null) {
            mValues = new int[mValuesSize];
            for(int i = 0; i < mValuesSize; i++) {
                int a = Integer.valueOf(MonthlyTopUser.get(1).get(i));
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

        float spaceForBar = 100f;

        hbChart.setDrawBarShadow(false);
        hbChart.setDrawValueAboveBar(true);
        hbChart.getDescription().setEnabled(false);
        hbChart.setPinchZoom(false);
        hbChart.setDrawGridBackground(false);


        XAxis xl = hbChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        xl.setValueFormatter(new IndexAxisValueFormatter(mResult));
//        CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
//        xl.setValueFormatter(xaxisFormatter);
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
        for (int i = 0; i < mResult.length; i++) {
            yVals1.add(new BarEntry(mResult.length-i-1, mValues[i]));
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

        ArrayList<String> mValues;

        CategoryBarChartXaxisFormatter(ArrayList<String> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {

            int val = (int) value;
            String label = "";
            if (val >= 0 && val < mValues.size()) {
                label = mValues.get(val);
            } else {
                label = "";
            }
            return label;
        }
    }

}
