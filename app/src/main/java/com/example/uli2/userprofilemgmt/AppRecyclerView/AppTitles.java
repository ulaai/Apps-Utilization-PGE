package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.content.Context;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uli on 04/08/17.
 */

public class AppTitles {
    static AppTitles appTitles;
    static int numLabel;
    List<AppTitleParent> titleParents;
    private List<String> label;
    private List<String> util_score;
    private List<String> num_actual;
    private List<String> num_registered;


    public AppTitles(Context context, List<List<String>> myDataset) {
        label = myDataset.get(0);
        num_actual = myDataset.get(1);
        num_registered = myDataset.get(2);
        util_score = myDataset.get(3);
        numLabel = label.size();

        titleParents = new ArrayList<>();
        for(int i = 0; i < label.size()-1; i++)
        {
            AppTitleParent title = new AppTitleParent(label.get(i), util_score.get(i));
            titleParents.add(title);
        }
    }

    public static AppTitles get(Context context, List<List<String>> myDataset)
    {
        if(appTitles == null)
            appTitles = new AppTitles(context, myDataset);
        return appTitles;
    }

    public List<AppTitleParent> getAll() {
        return titleParents;
    }

    public List<ParentObject> getChildren() {
        int i = 0;
        List<ParentObject> parentObject = new ArrayList<>();
        for(AppTitleParent title:titleParents)
        {
            List<Object> childList = new ArrayList<>();
            childList.add(new AppTitleChild(util_score.get(i),num_actual.get(i), num_registered
                    .get(i)));
            title.setChildObjectList(childList);
            parentObject.add(title);
            i++;
        }
        return parentObject;

    }


}
