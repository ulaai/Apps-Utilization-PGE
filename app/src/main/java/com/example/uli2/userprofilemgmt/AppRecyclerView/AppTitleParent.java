package com.example.uli2.userprofilemgmt.AppRecyclerView;

import com.bignerdranch.expandablerecyclerview.Model.ParentObject;

import java.util.List;
import java.util.UUID;

/**
 * Created by uli on 04/08/17.
 */

public class AppTitleParent implements ParentObject {
    private List<Object> mChildrenList;
    private UUID _id;
    private String title;
    private String util;

    public AppTitleParent(String title, String util) {
        this.title = title;
        this.util = util;
        _id = UUID.randomUUID();
    }
    @Override
    public List<Object> getChildObjectList() {
        return mChildrenList;
    }

    @Override
    public void setChildObjectList(List<Object> list) {
        mChildrenList = list;

    }

    public UUID get_id() {
        return _id;
    }

    public void set_id(UUID _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUtil() {
        return util;
    }

    public void setUtil(String util) {
        this.util = util;
    }


}
