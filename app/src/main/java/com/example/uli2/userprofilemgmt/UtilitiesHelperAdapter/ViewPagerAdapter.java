package com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.uli2.userprofilemgmt.AnnuallyFragment;
import com.example.uli2.userprofilemgmt.DailyFragment;
import com.example.uli2.userprofilemgmt.MonthlyFragment;

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
                return new AnnuallyFragment();
            case 1:
                return new MonthlyFragment();
            case 2:
                return new DailyFragment();
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
                return "Annually";
            case 1:
                return "Monthly";
            case 2:
                return "Daily";
        }
        return null;

    }
}
