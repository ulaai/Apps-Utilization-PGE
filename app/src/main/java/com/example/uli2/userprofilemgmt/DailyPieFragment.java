package com.example.uli2.userprofilemgmt;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;

import java.util.ArrayList;
import java.util.List;


public class DailyPieFragment extends Fragment {
    public DailyPieFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily_pie, container, false);
        PieChart pChart = (PieChart)rootView.findViewById(R.id.pie1chart);
        LineChart lChart = (LineChart)rootView.findViewById(R.id.line1chart);
        HorizontalBarChart hbChart = (HorizontalBarChart)rootView.findViewById(R.id.hb1chart);
        Button appButton = (Button)rootView.findViewById(R.id.appbutton);

        float mult = 100;
        final String[] mResult = new String[] { "High", "Low" };
        int[] mValues = new int[] {92, 8};
        int sumValues=0;
        int[] mPercent = new int[mValues.length];

        //getpercentage
        for (int mValue : mValues) {
            sumValues += mValue;
        }

        MakePieChart(pChart, mResult, mValues);

        MakeLineChart(lChart, mResult, mValues);

        MakeHorizontalBarChart(hbChart, mResult, mValues);


        appButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ApplicationActivity.class);
                Bundle bundle = null;

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    bundle = ActivityOptions.makeSceneTransitionAnimation((Activity) view.getContext())
                            .toBundle();
                }
                intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(intent, bundle);
            }
        });

        Button toalbum = (Button) rootView.findViewById(R.id.toalbum);
        toalbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ActivityA.class);
                startActivity(intent);
            }
        });

        return rootView;
    }

    private SpannableString generateCenterSpannableText() {

        SpannableString s = new SpannableString("92%");
        //make the text twice as large
        s.setSpan(new RelativeSizeSpan(8f), 0, 2, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, 2, 0);
        s.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.colorHigh)),
                0, 2, 0);
        return s;
    }

    private void MakePieChart(PieChart pChart, String[] mResult, int[] mValues) {
        pChart.setUsePercentValues(true);
        pChart.getDescription().setEnabled(false);
        pChart.setExtraOffsets(5, 10, 5, 5);
        pChart.setDragDecelerationFrictionCoef(0.95f);


        pChart.setDrawHoleEnabled(true);
        pChart.setHoleColor(Color.WHITE);

        pChart.setTransparentCircleColor(Color.WHITE);
        pChart.setTransparentCircleAlpha(110);

        pChart.setHoleRadius(58f);
        pChart.setTransparentCircleRadius(61f);

        pChart.setDrawCenterText(true);
        pChart.setCenterText(generateCenterSpannableText());

        pChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pChart.setRotationEnabled(true);
        pChart.setHighlightPerTapEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener



        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        for (int i = 0; i < mResult.length; i++) {
            entries.add(new PieEntry((float) mValues[i % mValues.length],
                    mResult[i % mResult.length]));
        }

        PieDataSet dataSet = new PieDataSet(entries, "Utilization");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //add colorssss to the pie
        colors.add(ContextCompat.getColor(getContext(), R.color.colorHigh));
        colors.add(ContextCompat.getColor(getContext(), R.color.colorLow));

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);



        pChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);


        Legend l = pChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);

        // entry label styling
        pChart.setEntryLabelColor(Color.WHITE);
        pChart.setEntryLabelTextSize(12f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        pChart.setData(data);

        // undo all highlights
        pChart.highlightValues(null);

        pChart.invalidate();

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
                Drawable drawable = ContextCompat.getDrawable(getContext(), R.drawable.fade_red);
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

    private void MakeHorizontalBarChart(HorizontalBarChart hbChart, String[] mResult, int[]
            mValues) {
        ArrayList<String> labels = new ArrayList<>();
        labels.add("January");
        labels.add("February");
        labels.add("March");
        labels.add("April");
        labels.add("May");
        labels.add("June");

        hbChart.setDrawBarShadow(false);
        hbChart.setDrawValueAboveBar(true);
        hbChart.getDescription().setEnabled(false);
        hbChart.setPinchZoom(false);
        hbChart.setDrawGridBackground(false);


        XAxis xl = hbChart.getXAxis();
        xl.setPosition(XAxis.XAxisPosition.BOTTOM);
        xl.setDrawAxisLine(true);
        xl.setDrawGridLines(false);
        CategoryBarChartXaxisFormatter xaxisFormatter = new CategoryBarChartXaxisFormatter(labels);
        xl.setValueFormatter(xaxisFormatter);
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
        for (int i = 0; i < 6; i++) {
            yVals1.add(new BarEntry(i, (i+1)*10));
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
    }
}
