package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

public class SplashScreen extends AppCompatActivity implements AsyncResponse {
    private boolean isConnected = false;
    private int executed = 0;
    Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", java.util.Locale.getDefault());
        String currdate = sdf.format(cal.getTime());

        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setMonthlyTotalUtilization();
        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setAnnuallyTotalUtilization();
        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setDailyTotalUtilization(currdate);

    }

    @Override
    public void processFinish(String output) {
        isConnected = true;
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
