package com.example.uli2.userprofilemgmt;

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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.Persistence.Users;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.ViewPagerAdapter;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;
    private Toolbar mToolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String[] pageTitle = {"Annually", "Monthly", "Daily"};
    AppDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager)findViewById(R.id.viewpager);
        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        TextView welcome_greetings = (TextView) findViewById(R.id.welcome_greetings);
        TextView currentuser_greetings = (TextView) findViewById(R.id.currentuser_greetings);

        getSupportActionBar().hide();
        mToolbar.setTitle("Apps Utilization PGE");

        database = AppDatabase.getDatabase(getApplicationContext());

//        change navigation drawer header and name programmatically in class file
        View hView =  mNavigationView.getHeaderView(0);
        TextView nav_user = (TextView)hView.findViewById(R.id.currentUserDisplayName);
        Users currentUser = database.usersModel().getLoggedInUser();
        String currentUserDisplayName = currentUser.userDisplayName;
        nav_user.setText(currentUserDisplayName);
        currentuser_greetings.setText(currentUserDisplayName);

        //create default navigation drawer toggle
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        //setting Tab layout (number of Tabs = number of ViewPager pages)


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

        ImageView cover = (ImageView) findViewById(R.id.cover);
        cover.setImageResource(R.drawable.rinjani);

        Menu menu = mNavigationView.getMenu();

        Calendar cal = Calendar.getInstance();
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

        if(timeOfDay >= 0 && timeOfDay < 12){
            welcome_greetings.setText("Good Morning, ");
        }else if(timeOfDay >= 12 && timeOfDay < 16){
            welcome_greetings.setText("Good Afternoon, ");
        }else if(timeOfDay >= 16 && timeOfDay < 21){
            welcome_greetings.setText("Good Evening, ");
        }else if(timeOfDay >= 21 && timeOfDay < 24){
            welcome_greetings.setText("Good Night, ");
        }


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
