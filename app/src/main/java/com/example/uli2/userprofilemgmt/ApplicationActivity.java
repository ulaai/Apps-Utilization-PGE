package com.example.uli2.userprofilemgmt;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApplicationActivity extends ActionBarActivity implements AsyncResponse, ListExpandListener {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    List<List<String>> input = new ArrayList<List<String>>();

    Connection con = null;
    Statement stmt = null;
    String db = "jdbc:jtds:sqlserver://ptmpgesqlsvrdev.pertamina.com:1433/UserProfileManagement";
    String uname = "sa";
    String pass = "sqlserver2012PGE";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        //add divider
        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(itemDecoration);
        //use this if recyclerview size is fixed
//        recyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

//        ConnectionClass connectionClass = new ConnectionClass();
//        connectionClass.execute("select ApplicationName from Applications");
        Singleton.getInstance().newSingleton();
        Singleton.getInstance().setDelegate(this);
        Singleton.getInstance().getApplicationActivity();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_app, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) // Press Back Icon
        {
            finish();
        }
        if (item.getItemId() == R.id.sort) {

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output) {
        progressBar.setVisibility(View.GONE);
        input = Singleton.getInstance().hashMap.get("MAU");
        mAdapter = new AppAdapter(input);
        recyclerView.setAdapter(mAdapter);

    }
    @Override
    public void onExpand() {
    }

    @Override
    public void onCollapse() {
    }

}
