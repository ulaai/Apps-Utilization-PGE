package com.example.uli2.userprofilemgmt;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityB extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_b);

        Toast.makeText(getApplicationContext(),Singleton.getInstance().getString(), Toast.LENGTH_SHORT).show();
    }
}
