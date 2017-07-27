package com.example.uli2.userprofilemgmt;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class ActivityA extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity);

        Toast.makeText(getApplicationContext(), Singleton.getInstance().getString(), Toast
                .LENGTH_LONG).show();

        Singleton.getInstance().setString("Singleton");
        Intent intent = new Intent(ActivityA.this, ActivityB.class);
        this.startActivity(intent);
    }
}
