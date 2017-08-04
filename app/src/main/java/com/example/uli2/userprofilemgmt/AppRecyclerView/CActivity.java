package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.uli2.userprofilemgmt.R;

import java.util.ArrayList;
import java.util.List;

public class CActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    List<List<String>> input = new ArrayList<List<String>>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c);
        recyclerView = (RecyclerView)findViewById(R.id.myRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        CAdapter adapter = new CAdapter(this,initData());
        adapter.setParentClickableViewAnimationDefaultDuration();
        adapter.setParentAndIconExpandOnClick(true);

        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        ((CAdapter)recyclerView.getAdapter()).onSaveInstanceState(outState);
    }

    public List<ParentObject> initData() {
        AppTitles titleCreator = AppTitles.get(this, input);
        List<AppTitleParent> titles = titleCreator.getAll();
        List<ParentObject> parentObject = new ArrayList<>();
        for(AppTitleParent title:titles)
        {
            List<Object> childList = new ArrayList<>();
            childList.add(new AppTitleChild("Add to contacts","Send message"));
            title.setChildObjectList(childList);
            parentObject.add(title);
        }
        return parentObject;

    }


}
