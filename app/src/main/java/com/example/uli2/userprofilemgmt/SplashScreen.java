package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AsyncResponse;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class SplashScreen extends AppCompatActivity implements AsyncResponse {
    private boolean isConnected = false;
    private int executed = 0;
    Calendar cal;
    AppDatabase database;
    int annualcount, monthlycount, dailycount, daytoday, monthtoday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        String currdate = sdf.format(cal.getTime());

        database = AppDatabase.getDatabase(getApplicationContext());
        annualcount = database.annuallyPieModel().getCount();
        if(annualcount <= 0) {
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setAnnuallyTotalUtilization();
            isConnected = true;
        } else {
            executed = executed+1;
        }

        dailycount = database.dailyPieModel().getCount();
        daytoday = database.dailyPieModel().getCountDate(currdate);
        if(dailycount <= 0 || daytoday <= 0) {
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setDailyTotalUtilization(currdate);
            isConnected = true;

        } else {
            executed = executed+1;
        }

        monthlycount = database.monthlyPieModel().getCount();
        monthtoday = database.monthlyPieModel().getCountDate(currdate);
        if(monthlycount <= 0 || monthtoday <= 0) {
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setMonthlyTotalUtilization(currdate);
            isConnected = true;

        } else {
            executed = executed+1;
        }


        if(executed == 3 && !isConnected) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void processFinish(String output) {
        executed = executed+1;
//        Singleton.getInstance().MonthlyTotalUtilization = Singleton.getInstance().results;
//        Singleton.getInstance().hashMap.put("MTU", Singleton.getInstance().MonthlyTotalUtilization);
        if(executed == 3) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
