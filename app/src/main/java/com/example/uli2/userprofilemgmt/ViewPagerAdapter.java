package com.example.uli2.userprofilemgmt;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by uli2 on 10/07/17.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new DailyPieFragment();
            case 1:
                return new MonthlyFragment();
            case 2:
                return new DailyPieFragment();
        }
        return null;

    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "Domain";
            case 1:
                return "Upload";
            case 2:
                return "Download";
        }
        return null;

    }
}
