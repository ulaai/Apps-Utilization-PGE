package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] pageTitle = {"Fragment 1", "Fragment 2", "Fragment 3"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        getSupportActionBar().hide();
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("User Profile Management");

//        change navigation drawer header and name programmatically in class file
        View hView =  mNavigationView.getHeaderView(0);
//        TextView nav_user = (TextView)hView.findViewById(R.id.nav_name);
//        nav_user.setText(user);

        //create default navigation drawer toggle
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setting Tab layout (number of Tabs = number of ViewPager pages)
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        for (int i = 0; i < 3; i++) {
            tabLayout.addTab(tabLayout.newTab().setText(pageTitle[i]));
//            View tab = LayoutInflater.from(Main2Activity.this).inflate(R.layout.custom_tab, null);
//            TextView tv = (TextView) tab.findViewById(R.id.custom_text);
//            tv.setText(pageTitle[i]);
        }
        //set gravity for tab bar
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);

        assert mNavigationView != null;
        mNavigationView.setNavigationItemSelectedListener(this);

        //set viewpager adapter
        ViewPagerAdapter pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);

        //change Tab selection when swipe ViewPager
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        item.setChecked(true);

        int id = item.getItemId();

        if (id == R.id.fr1) {
            viewPager.setCurrentItem(0);
        } else if (id == R.id.fr2) {
            viewPager.setCurrentItem(1);
        } else if (id == R.id.fr3) {
            viewPager.setCurrentItem(2);
        } else if (id == R.id.close) {
            finish();
        }

        mDrawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        assert mDrawerLayout != null;
        if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        } else {
            if (viewPager.getCurrentItem() != 0) {
                viewPager.setCurrentItem(viewPager.getCurrentItem() - 1, true);
            } else {
                super.onBackPressed();
            }
        }
    }
}
