package com.example.uli2.userprofilemgmt.AppRecyclerView;

import android.content.Context;
import android.util.Log;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;
import com.example.uli2.userprofilemgmt.Persistence.Annually;
import com.example.uli2.userprofilemgmt.Persistence.AppDatabase;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

/**
 * Created by uli on 04/08/17.
 */

public class AppTitles {
    static AppTitles appTitles;
    static int numLabel;
    List<AppTitleParent> titleParents;
    private List<String> label = new ArrayList<>();
    private List<String> util_score = new ArrayList<>();
    private List<String> num_actual = new ArrayList<>();
    private List<String> num_registered = new ArrayList<>();

    public AppTitles(Context context, AppDatabase database) {
        List<Annually> annually = database.annuallyModel().getAllAnnually();
        numLabel = database.annuallyModel().getCount();

        for(int i = 0; i < numLabel; i++) {
            String mLabel = annually.get(i).label;
            String mActual = annually.get(i).actual;
            String mRegistered = annually.get(i).registered;
            String mUtil = annually.get(i).utilization;

            label.add(mLabel);
            num_actual.add(mActual);
            num_registered.add(mRegistered);
            util_score.add(mUtil);

        }


        titleParents = new ArrayList<>();
        for(int i = 0; i < label.size()-1; i++)
        {
            AppTitleParent title = new AppTitleParent(label.get(i), util_score.get(i));
            titleParents.add(title);
        }
    }

    public AppTitles(Context context, List<List<String>> myDataset, AppDatabase database) {
        label = myDataset.get(0);
        num_actual = myDataset.get(1);
        num_registered = myDataset.get(2);
        util_score = myDataset.get(3);
        numLabel = label.size();


        titleParents = new ArrayList<>();
        for(int i = 0; i < label.size(); i++)
        {
            AppTitleParent title = new AppTitleParent(label.get(i), util_score.get(i));
            titleParents.add(title);
            Annually annually = database.annuallyModel().getTask(i);
            if(annually != null) {
                annually.label = label.get(i);
                annually.actual = num_actual.get(i);
                annually.registered = num_registered.get(i);
                annually.utilization = util_score.get(i);
                annually.date = "2017";
            }
            else {
                Annually build = Annually.builder()
                        .setId(i)
                        .setLabel(label.get(i))
                        .setActual(num_actual.get(i))
                        .setRegistered(num_registered.get(i))
                        .setUtilization(util_score.get(i))
                        .setDate("2017")
                        .build();
                database.annuallyModel().addAnnually(build);

            }

        }
    }

    public static AppTitles get(Context context, List<List<String>> myDataset, AppDatabase database)
    {
        if(appTitles == null)
            appTitles = new AppTitles(context, myDataset, database);
        return appTitles;
    }

    public static AppTitles get(Context context, AppDatabase database)
    {
        if(appTitles == null)
            appTitles = new AppTitles(context, database);
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
    public void sortbyUtil() {
        Collections.sort(titleParents, new Comparator<AppTitleParent>() {
            @Override
            public int compare(AppTitleParent p1, AppTitleParent p2) {
                if(Objects.equals(p1.getUtil(), "null")) p1.setUtil("0");
                if(Objects.equals(p2.getUtil(), "null")) p2.setUtil("0");

                return Integer.valueOf(p1.getUtil()) - Integer.valueOf(p2.getUtil()); // Ascending
            }
        });
        for(int i = 0; i < titleParents.size(); i++) {
            Log.d("myTag", titleParents.get(i).getTitle() + " " + titleParents.get(i).getUtil());
        }

    }


}
