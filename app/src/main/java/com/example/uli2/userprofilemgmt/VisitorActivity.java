package com.example.uli2.userprofilemgmt;

import android.content.Context;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.example.uli2.userprofilemgmt.Persistence.Annually;
import com.example.uli2.userprofilemgmt.Persistence.AnnuallyVisitors;
import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.Persistence.DailyVisitors;
import com.example.uli2.userprofilemgmt.Persistence.MonthlyVisitors;
import com.example.uli2.userprofilemgmt.Persistence.TopUsers;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AsyncResponse;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;

public class VisitorActivity extends AppCompatActivity implements AsyncResponse {
    LineChart lChart;
    List<List<String>> MonthlyVisitor;
    String[] mResult;
    int[] mValues;
    AppDatabase database;
    int count;
    boolean isOnDb;
    Calendar cal;
    String currdate, sourceFragment;
    ArrayList<Integer> mValuesA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        lChart = (LineChart) findViewById(R.id.line1chart);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        sourceFragment = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            sourceFragment = extras.getString("sourceFragment");
            Log.d("myTag", sourceFragment);
        }
        String title = sourceFragment + " Visitor";
        getSupportActionBar().setTitle(title);

        cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale
                .getDefault());
        currdate = sdf.format(cal.getTime());

        Singleton.getInstance().setDelegate(this);
        database = AppDatabase.getDatabase(getApplicationContext());

        if(Objects.equals(sourceFragment, "Annually")) {
            AnnuallyVisitors visitors = database.annuallyVisitorsModel().getVisitor(0);
            if(visitors == null) {
                Singleton.getInstance().setAnnuallyVisitor();
                isOnDb = false;
            } else {
                isOnDb = true;
                MakeLineChart(lChart);
            }
        } else if(Objects.equals(sourceFragment, "Monthly")) {
            List<MonthlyVisitors> visitors = database.monthlyVisitorsModel()
                    .getAllMonthlyVisitors();
            if(visitors.size() == 0) {
                Singleton.getInstance().setMonthlyVisitor(currdate);
                isOnDb = false;
            } else {
                isOnDb = true;
                MakeLineChart(lChart);
            }

        } else {
            List<DailyVisitors> visitors = database.dailyVisitorsModel().getAllDailyVisitors();
            if(visitors.size() == 0) {
                Singleton.getInstance().setDailyVisitor(currdate);
                isOnDb = false;
            } else {
                isOnDb = true;
                MakeLineChart(lChart);
            }

        }

    }

    @Override
    public void processFinish(String output) {
        MakeLineChart(lChart);
    }

    @Override
    public Context getDelegateContext() {
        return null;
    }

    private void MakeLineChart(LineChart lChart) {

        if(!isOnDb) {
            if (Objects.equals(sourceFragment, "Annually")) {
                MonthlyVisitor = Singleton.getInstance().hashMap.get("AV");
            } else if (Objects.equals(sourceFragment, "Monthly")) {
                MonthlyVisitor = Singleton.getInstance().hashMap.get("MV");
            } else {
                MonthlyVisitor = Singleton.getInstance().hashMap.get("DV");
            }

            if (mResult == null && mValues == null) {
                int mResultSize = MonthlyVisitor.get(MonthlyVisitor.size()-2).size();
                int mValuesSize = MonthlyVisitor.get(MonthlyVisitor.size()-1).size();

                mResult = new String[mResultSize];
                mValues = new int[mValuesSize];
                mValuesA = new ArrayList<>();

                for (int i = 0; i < mResultSize; i++) {
                    String a = MonthlyVisitor.get(MonthlyVisitor.size()-2).get(mResultSize - i - 1);
                    if (a.length() > 15) {
                        String anew = a.substring(a.lastIndexOf(" ") + 1);
                        int k = a.lastIndexOf(" ");
                        char[] aInChars = a.toCharArray();
                        aInChars[k] = '\n';
                        a = String.valueOf(aInChars);
                    }
                    mResult[i] = a;

                    String before = MonthlyVisitor.get(MonthlyVisitor.size()-1).get(i);
                    int b;
                    if (Objects.equals(before, "null")) {
                        b = 0;
                    } else {
                        b = Integer.valueOf(before);
                    }

                    mValues[i] = b;
                    mValuesA.add(b);

                }
                if (Objects.equals(sourceFragment, "Annually")) {
                    for (int i = 0; i < mResultSize; i++) {
                        String a = mResult[mResult.length - i - 1];
                        int b = mValues[i];
                        AnnuallyVisitors build = AnnuallyVisitors.builder()
                                .setId(i)
                                .setLabels(a)
                                .setValues(Integer.toString(b))
                                .build();
                        database.annuallyVisitorsModel().addAnnuallyVisitors(build);

                    }

                } else if (Objects.equals(sourceFragment, "Monthly")) {

                    for (int i = 0; i < mResultSize; i++) {
                        String a = mResult[mResult.length - i - 1];
                        int b = mValues[i];
                        MonthlyVisitors build = MonthlyVisitors.builder()
                                .setId(i)
                                .setLabels(a)
                                .setValues(Integer.toString(b))
                                .build();
                        database.monthlyVisitorsModel().addMonthlyVisitors(build);

                    }

                } else {

                    for (int i = 0; i < mResultSize; i++) {
                        String a = mResult[mResult.length - i - 1];
                        int b = mValues[i];
                        DailyVisitors build = DailyVisitors.builder()
                                .setId(i)
                                .setLabels(a)
                                .setValues(Integer.toString(b))
                                .build();
                        database.dailyVisitorsModel().addDailyVisitors(build);

                    }
                }

            }
        } else {
            if (Objects.equals(sourceFragment, "Annually")) {

                List<AnnuallyVisitors> AnnuallyVisitors = database.annuallyVisitorsModel()
                        .getAllAnnuallyVisitors();
                int numSize = AnnuallyVisitors.size();
                mResult = new String[numSize];
                mValues = new int[numSize];
                for (int i = 0; i < numSize; i++) {
                    mResult[i] = AnnuallyVisitors.get(i).labels;
                    int a = Integer.valueOf(AnnuallyVisitors.get(i).values);
                    mValues[i] = a;
                }

            } else if (Objects.equals(sourceFragment, "Monthly")) {

                List<MonthlyVisitors> MonthlyVisitors = database.monthlyVisitorsModel().getAllMonthlyVisitors();
                int numSize = MonthlyVisitors.size();
                mResult = new String[numSize];
                mValues = new int[numSize];
                for (int i = 0; i < numSize; i++) {
                    mResult[i] = MonthlyVisitors.get(i).labels;
                    int a = Integer.valueOf(MonthlyVisitors.get(i).values);
                    mValues[i] = a;
                }
            } else {

                List<DailyVisitors> DailyVisitors = database.dailyVisitorsModel()
                        .getAllDailyVisitors();
                int numSize = DailyVisitors.size();
                mResult = new String[numSize];
                mValues = new int[numSize];
                for (int i = 0; i < numSize; i++) {
                    mResult[i] = DailyVisitors.get(i).labels;
                    int a = Integer.valueOf(DailyVisitors.get(i).values);
                    mValues[i] = a;
                }
            }



        }


        List<String> labels = new ArrayList<>();
        ArrayList<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < mValues.length; i++) {
            entries.add(new Entry(i, mValues[i]));

            if(Objects.equals(sourceFragment, "Annually")) {
                labels.add(mResult[mResult.length-i-1]);

            } else if(Objects.equals(sourceFragment, "Monthly")) {
                labels.add(mResult[mResult.length-i-1].substring(5,7));

            } else {
                labels.add(mResult[mResult.length-i-1].substring(8,10));
            }
        }

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
        xAxis.setLabelCount(mResult.length);
        xAxis.setGranularity(1);

        YAxis axisLeft = lChart.getAxisLeft();
        axisLeft.removeAllLimitLines();
        axisLeft.setDrawGridLines(true);
        axisLeft.setGridColor(getResources().getColor(R.color.colorLow));
        axisLeft.setDrawAxisLine(false);
//        axisLeft.setLabelCount(4, false);
        axisLeft.setDrawLabels(true);
        axisLeft.setValueFormatter(new getValueFormatter(mValuesA));

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

    public class getValueFormatter implements IAxisValueFormatter {

        private ArrayList<Integer> mValues;

        public getValueFormatter(ArrayList<Integer> values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            // "value" represents the position of the label on the axis (x or y)
            return ""+((int)value);
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
