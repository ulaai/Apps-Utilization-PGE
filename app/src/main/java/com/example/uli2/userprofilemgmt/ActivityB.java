package com.example.uli2.userprofilemgmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);
        List<List<String>> a = Singleton.getInstance().MonthlyTotalUtilization;

        Toast.makeText(getApplicationContext(),String.valueOf(a.size()), Toast.LENGTH_SHORT).show();
    }
}
