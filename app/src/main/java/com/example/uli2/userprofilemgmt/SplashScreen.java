package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.List;

public class SplashScreen extends AppCompatActivity implements AsyncResponse {
    private boolean isConnected = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getSupportActionBar().hide();
        Singleton.getInstance().newSingleton();
        Singleton.getInstance().setDelegate(SplashScreen.this);
        Singleton.getInstance().setMonthlyTotalUtilization();

//        Thread myThread = new Thread() {
//            @Override
//            public void run() {
//                try {
//                    sleep(3000);
//                    if(isConnected) {
//                        finish();
//                    }
//                    else {
//                        finish();
//                    }
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        };
//
//
//        myThread.start();
    }

    @Override
    public void processFinish(String output) {
        isConnected = true;
        Singleton.getInstance().MonthlyTotalUtilization = Singleton.getInstance().results;
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
        finish();
    }

}
