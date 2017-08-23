package com.example.uli2.userprofilemgmt;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.uli2.userprofilemgmt.Persistence.AnnuallyPie;
import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.Persistence.DailyPie;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.Album;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AlbumsAdapter;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AsyncResponse;
import com.github.anastr.speedviewlib.SpeedView;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class DailyFragment extends Fragment implements AsyncResponse {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private ImageView thumbnail;
    PieChart pChart;
    Calendar cal;
    TextView txtDate;
    String currdate, icurrdate;
    DatePickerDialog.OnDateSetListener datePicker;
    CoordinatorLayout.Behavior behavior;
    boolean changed = false;
    AppDatabase database;
    int count, average = -1;
    String[] mResult;
    int[] mValues;
    DailyPie dailyPie;
    SpeedView gChart;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_daily, container, false);
        pChart = (PieChart)rootView.findViewById(R.id.pie1chart);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        gChart = (SpeedView) rootView.findViewById(R.id.speedView);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(rootView.getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 4);
        recyclerView.setLayoutManager(mLayoutManager);
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        database = AppDatabase.getDatabase(getActivity());

        txtDate = (TextView) rootView.findViewById(R.id.txtDate);
        cal = Calendar.getInstance();
        SimpleDateFormat sdfTxt = new SimpleDateFormat("EEEE, d MMM yyyy", java.util.Locale
                .getDefault());
        icurrdate = sdfTxt.format(cal.getTime());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale
                .getDefault());
        currdate = sdf.format(cal.getTime());

        txtDate.setText(icurrdate);
        MakePieChart(pChart);

        datePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int
                    dayOfMonth) {
                if(datePicker.isShown()) {
                    cal.set(Calendar.YEAR, year);
                    cal.set(Calendar.MONTH, monthOfYear);
                    cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    pickDate();

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale
                            .getDefault());
                    currdate = sdf.format(cal.getTime());
                    count = database.dailyPieModel().getCountDate(currdate);
                    if(count <= 0) {
                        Singleton.getInstance().setDelegate(DailyFragment.this);
                        Singleton.getInstance().setDailyTotalUtilization(currdate);
                        Singleton.getInstance().setDelegate(DailyFragment.this);
                        Singleton.getInstance().setDailyAverageUtilization(currdate);
                    } else {
                        MakePieChart(pChart);
                    }
                }
            }
        };

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(getActivity(), datePicker, cal.get(Calendar.YEAR), cal
                        .get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH)).show();
                Log.d("myTag", "change date button clicked");
            }
        });

        return rootView;
    }

    private void prepareAlbums() {
        int[] icons = new int[]{
                R.drawable.util_icon,
                R.drawable.topapp_icon,
                R.drawable.topuser_icon,
                R.drawable.visitor_icon
        };

        Album a = new Album("Utilization",  icons[0], ".ApplicationActivity", "Daily");
        albumList.add(a);

        a = new Album("Top Application", icons[1], ".TopApplicationActivity", "Daily");
        albumList.add(a);

        a = new Album("Top User", icons[2], ".TopUserActivity", "Daily");
        albumList.add(a);

        a = new Album("Visitor", icons[3], ".VisitorActivity", "Daily");
        albumList.add(a);

        adapter.notifyDataSetChanged();
    }

    private void pickDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, d MMM yyyy", java.util.Locale.getDefault());
        String currdate = sdf.format(cal.getTime());
        txtDate.setText(currdate);
    }

    @Override
    public void processFinish(String output) {
        changed = true;
        MakePieChart(pChart);
    }

    @Override
    public Context getDelegateContext() {
        return getActivity().getApplicationContext();
    }

    /**
     * RecyclerView item decoration - give equal margin around grid item
     */
    public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private int spanCount;
        private int spacing;
        private boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount; // spacing - column * ((1f / spanCount) * spacing)
                outRect.right = (column + 1) * spacing / spanCount; // (column + 1) * ((1f / spanCount) * spacing)

                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount; // column * ((1f / spanCount) * spacing)
                outRect.right = spacing - (column + 1) * spacing / spanCount; // spacing - (column + 1) * ((1f /    spanCount) * spacing)
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    /**
     * Converting dp to pixel
     */
    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

    private SpannableString generateCenterSpannableText(int average) {
        int len = Integer.toString(average).length();
        SpannableString s = new SpannableString(Integer.toString(average));
        //make the text twice as large
        s.setSpan(new RelativeSizeSpan(8f), 0, len, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0, len, 0);
        s.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.colorHigh)),
                0, len, 0);
        return s;
    }

    private void MakePieChart(PieChart pChart) {
        int average = 0;
        count = database.dailyPieModel().getCountDate(currdate);
        if(count <= 0) {
            //        Preparing data for Monthly Total Utilization
            List<List<String>> DailyTotalUtilization = Singleton.getInstance().hashMap.get("DTU");
            int numSize = DailyTotalUtilization.get(0).size();
            int dtuSize = DailyTotalUtilization.size();
            mResult = new String[] {"High", "Medium", "Low"};

            mValues = new int[numSize];
            for(int i = 0; i < DailyTotalUtilization.get(dtuSize-1).size(); i++) {
                int a = Integer.valueOf(DailyTotalUtilization.get(dtuSize-1).get(i));
                mValues[i] = a;
            }

            List<List<String>> DailyAverageUtilization = Singleton.getInstance().hashMap.get
                    ("DAU");

            DailyPie build = DailyPie.builder()
                    .setHigh(Integer.toString(mValues[0]))
                    .setMedium(Integer.toString(mValues[1]))
                    .setLow(Integer.toString(mValues[2]))
                    .setDate(currdate)
                    .build();
            database.dailyPieModel().addDailyPie(build);

            dailyPie = database.dailyPieModel().getDailyPie(0);
            average = Integer.valueOf(DailyAverageUtilization.get(DailyAverageUtilization.size()
                    -1).get(0));

        } else {
            List<DailyPie> DailyTotalUtilization = database.dailyPieModel()
                    .getPieDate(currdate);
            List<List<String>> DailyAverageUtilization = Singleton.getInstance().hashMap.get
                    ("DAU");

            mResult = new String[] {"High", "Medium", "Low"};
            mValues = new int[3];
            for(int i = 0; i < 3; i++) {
                int a = Integer.valueOf(DailyTotalUtilization.get(0).getAttribute(i));
                mValues[i] = a;
            }
            if(DailyTotalUtilization.get(0).getAverage() != null) {
                average = Integer.valueOf(DailyTotalUtilization.get(0).getAverage());
            } else {
                average = Integer.valueOf(DailyAverageUtilization.get(DailyAverageUtilization.size()
                        -1).get(0));
            }
        }

        if(changed) {
            pChart.getData().removeDataSet(pChart.getData().getDataSet());
        }
        pChart.getLegend().setEnabled(false);
        pChart.setUsePercentValues(true);
        pChart.getDescription().setEnabled(false);
        pChart.setExtraOffsets(5, 10, 5, 5);
        pChart.setDragDecelerationFrictionCoef(0.95f);

        pChart.setDrawHoleEnabled(false);
        pChart.setHoleColor(Color.WHITE);
        pChart.setHoleRadius(58f);

        pChart.setTransparentCircleColor(Color.WHITE);
        pChart.setTransparentCircleAlpha(110);
        pChart.setTransparentCircleRadius(61f);

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
            if(!Objects.equals(mResult[i], "Average")) {
                entries.add(new PieEntry((float) mValues[i],
                        mResult[i]));
            } else {
                average = mValues[i];
            }
        }
//        pChart.setDrawCenterText(true);
//        pChart.setCenterText(generateCenterSpannableText(average));

        PieDataSet dataSet = new PieDataSet(entries, "Utilization");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //add colorssss to the pie
        colors.add(ContextCompat.getColor(getContext(), R.color.colorHigh));
        colors.add(ContextCompat.getColor(getActivity().getApplicationContext(), R.color
                .colorMedium));
        colors.add(ContextCompat.getColor(getContext(), R.color.colorLow));
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
        pChart.notifyDataSetChanged();
        pChart.invalidate();

        //make speedview
        if(average != -1) {
            gChart.speedTo((float)average, 4000);
        } else {
            gChart.speedTo(50, 4000);
        }
        gChart.setWithTremble(false);

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
