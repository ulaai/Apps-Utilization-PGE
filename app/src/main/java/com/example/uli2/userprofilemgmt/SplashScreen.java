package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SplashScreen extends AppCompatActivity implements AsyncResponse {
    private boolean isConnected = false;
    private int executed = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setMonthlyTotalUtilization();
        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setAnnuallyTotalUtilization();


    }

    @Override
    public void processFinish(String output) {
        isConnected = true;
        executed = executed+1;
//        Singleton.getInstance().MonthlyTotalUtilization = Singleton.getInstance().results;
//        Singleton.getInstance().hashMap.put("MTU", Singleton.getInstance().MonthlyTotalUtilization);
        if(executed == 2) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

}
