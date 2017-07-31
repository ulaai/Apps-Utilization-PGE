package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ActivityA extends AppCompatActivity {
    TextView textView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);
        textView2 = (TextView) findViewById(R.id.textView2);

        Toast.makeText(getApplicationContext(), Singleton.getInstance().getString(), Toast
                .LENGTH_LONG).show();

        Singleton.getInstance().setString("Singleton");
        List<List<String>> a = Singleton.getInstance().MonthlyTotalUtilization;
        Log.d("myTag", String.valueOf(a.size()));
        textView2.setText(String.valueOf(a.size()));
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
        this.startActivity(intent);
    }
}
