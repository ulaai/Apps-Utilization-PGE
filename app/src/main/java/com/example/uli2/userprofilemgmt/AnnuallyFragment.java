package com.example.uli2.userprofilemgmt;

import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.uli2.userprofilemgmt.Persistence.Annually;
import com.example.uli2.userprofilemgmt.Persistence.AnnuallyPie;
import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.Album;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AlbumsAdapter;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.MPPointF;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class AnnuallyFragment extends Fragment {
    private RecyclerView recyclerView;
    private AlbumsAdapter adapter;
    private List<Album> albumList;
    private ImageView thumbnail;
    CoordinatorLayout.Behavior behavior;
    AppDatabase database;
    int count;
    String[] mResult;
    int[] mValues;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_annually, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);

        albumList = new ArrayList<>();
        adapter = new AlbumsAdapter(rootView.getContext(), albumList);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(rootView.getContext(), 2);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(10), true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        prepareAlbums();

        database = AppDatabase.getDatabase(getActivity());
        count = database.annuallyPieModel().getCount();
        if(count <= 0) {
            List<List<String>> AnnuallyTotalUtilization = Singleton.getInstance().hashMap.get("ATU");
            mResult = new String[AnnuallyTotalUtilization.get(0).size()];

            for(int i = 0; i < AnnuallyTotalUtilization.get(0).size(); i++) {
                String a = AnnuallyTotalUtilization.get(0).get(i);
                mResult[i] = a;
            }

            mValues = new int[AnnuallyTotalUtilization.get(1).size()];
            for(int i = 0; i < AnnuallyTotalUtilization.get(1).size(); i++) {
                int a = Integer.valueOf(AnnuallyTotalUtilization.get(1).get(i));
                mValues[i] = a;
            }

            AnnuallyPie build = AnnuallyPie.builder()
                    .setId(0)
                    .setAverage(Integer.toString(mValues[0]))
                    .setHigh(Integer.toString(mValues[1]))
                    .setMedium(Integer.toString(mValues[2]))
                    .setLow(Integer.toString(mValues[3]))
                    .build();
            database.annuallyPieModel().addAnnuallyPie(build);

        } else {
            List<AnnuallyPie> AnnuallyTotalUtilization = database.annuallyPieModel()
                    .getAllAnnuallyPie();
            mResult = new String[] {"Average", "High", "Medium", "Low"};
            mValues = new int[4];
            for(int i = 0; i < 4; i++) {
                int a = Integer.valueOf(AnnuallyTotalUtilization.get(0).getAttribute(i));
                mValues[i] = a;
            }

        }

        PieChart pChart = (PieChart)rootView.findViewById(R.id.pie1chart);

        MakePieChart(pChart, mResult, mValues);

        return rootView;
    }


    /**
     * Adding few albums for testing
     */
    private void prepareAlbums() {
        int[] icons = new int[]{
                R.drawable.util_icon,
                R.drawable.topapp_icon,
                R.drawable.topuser_icon,
                R.drawable.visitor_icon
        };

        Album a = new Album("Utilization",  icons[0], ".ApplicationActivity", "Annually");
        albumList.add(a);

        a = new Album("Top Application", icons[1], ".TopApplicationActivity", "Annually");
        albumList.add(a);

        a = new Album("Top User", icons[2], ".TopUserActivity", "Annually");
        albumList.add(a);

        a = new Album("Visitor", icons[3], ".VisitorActivity", "Annually");
        albumList.add(a);

        adapter.notifyDataSetChanged();
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
    private void MakePieChart(PieChart pChart, String[] mResult, int[] mValues) {
        int average = 0;

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

        pChart.setDrawCenterText(true);
        pChart.setCenterText(generateCenterSpannableText(average));

        PieDataSet dataSet = new PieDataSet(entries, "Utilization");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        //add colorssss to the pie
        colors.add(ContextCompat.getColor(getActivity().getApplicationContext(), R.color
                .colorHigh));
        colors.add(ContextCompat.getColor(getActivity().getApplicationContext(), R.color
                .colorMedium));

        colors.add(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.colorLow));

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
    private SpannableString generateCenterSpannableText(int average) {
        int len = Integer.toString(average).length();
        SpannableString s = new SpannableString(Integer.toString(average));
        //make the text twice as large
        s.setSpan(new RelativeSizeSpan(8f), 0, len, 0);
        s.setSpan(new StyleSpan(Typeface.NORMAL), 0,len, 0);
        s.setSpan(new ForegroundColorSpan(this.getResources().getColor(R.color.colorHigh)),
                0, len, 0);
        return s;
    }

}
