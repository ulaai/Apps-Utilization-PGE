package com.example.uli2.userprofilemgmt;

import android.content.Context;
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
    String annualaverage;

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
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setAnnuallyAverageUtilization();

            isConnected = true;
        } else {
            executed = executed+2;
        }


        dailycount = database.dailyPieModel().getCount();
        daytoday = database.dailyPieModel().getCountDate(currdate);
        if(dailycount <= 0 || daytoday <= 0) {
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setDailyTotalUtilization(currdate);
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setDailyAverageUtilization(currdate);

            isConnected = true;

        } else {
            executed = executed+2;
        }

        monthlycount = database.monthlyPieModel().getCount();
        monthtoday = database.monthlyPieModel().getCountDate(currdate);
        if(monthlycount <= 0 || monthtoday <= 0) {
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setMonthlyTotalUtilization(currdate);
            Singleton.getInstance().setDelegate(SplashScreen.this);
            Singleton.getInstance().setMonthlyAverageUtilization(currdate);

            isConnected = true;

        } else {
            executed = executed+2;
        }


        if(executed == 6 && !isConnected) {
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
        if(executed == 6) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public Context getDelegateContext() {
        return this;
    }

}
