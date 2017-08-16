package com.example.uli2.userprofilemgmt;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.uli2.userprofilemgmt.AppRecyclerView.AppTitleParent;
import com.example.uli2.userprofilemgmt.AppRecyclerView.AppTitles;
import com.example.uli2.userprofilemgmt.AppRecyclerView.CAdapter;
import com.example.uli2.userprofilemgmt.Persistence.Annually;
import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;
import com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter.AsyncResponse;

import java.sql.Connection;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ApplicationActivity extends AppCompatActivity implements AsyncResponse {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    List<List<String>> input = new ArrayList<List<String>>();
    AppDatabase database;
    int count;

    Connection con = null;
    Statement stmt = null;
    String db = "jdbc:jtds:sqlserver://ptmpgesqlsvrdev.pertamina.com:1433/UserProfileManagement";
    String uname = "sa";
    String pass = "sqlserver2012PGE";

    CAdapter adapter;
    AppTitles titleCreator;

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

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String value = extras.getString("sourceFragment");
            Log.d("myTag", value);
        }

        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        database = AppDatabase.getDatabase(getApplicationContext());
        count = database.annuallyModel().getCount();
        Log.d("myTag", String.valueOf(count));
        if(count <= 0) {
            Singleton.getInstance().newSingleton();
            Singleton.getInstance().setDelegate(this);
            Singleton.getInstance().getApplicationActivity();
            Log.d("myTag", "count <= 0");

        } else {
            onFinish();
        }

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
            buildAndShowInputDialog();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void processFinish(String output) {
        progressBar.setVisibility(View.GONE);
        input = Singleton.getInstance().hashMap.get("MAU");
        adapter = new CAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);


    }

    public List<ParentObject> initData() {
        if(count <= 0) {
            titleCreator = AppTitles.get(this, input, database);
        }
        else {
            titleCreator = AppTitles.get(this, database);
        }
        List<AppTitleParent> titles = titleCreator.getAll();
        List<ParentObject> parentObject = titleCreator.getChildren();
        return parentObject;

    }

    public void onFinish() {
        progressBar.setVisibility(View.GONE);
        adapter = new CAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);
        recyclerView.setAdapter(adapter);
    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((CAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
    }


    private void buildAndShowInputDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationActivity.this);
        builder.setTitle("Sort")
                .setItems(R.array.sortArray, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                Log.d("myTag", "first option");
                                dialog.dismiss();
                                break;
                            case 1:
                                titleCreator.sortbyUtil();
                                recyclerView.setAdapter(null);
                                List<ParentObject> parentObject = titleCreator.getChildren();
                                adapter = new CAdapter(ApplicationActivity.this, parentObject);
                                recyclerView.setAdapter(adapter);
                                dialog.dismiss();
                                break;
                        }
                    }
                });
        builder.show();

    }
}
