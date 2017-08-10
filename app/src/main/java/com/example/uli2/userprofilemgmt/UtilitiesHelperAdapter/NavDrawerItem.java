package com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter;

/**
 * Created by uli2 on 10/07/17.
 */

public class NavDrawerItem {
    private String title;
    private int icon;
    private String count = "0";

    // boolean to set visibility of the counter
    private boolean isCounterVisible = false;

    public NavDrawerItem() {}

    public NavDrawerItem(String title, int icon) {
        this.title = title;
        this.icon = icon;
    }

    public NavDrawerItem(String title, int icon, String count, boolean isCounterVisible) {
        this.title = title;
        this.icon = icon;
        this.count = count;
        this.isCounterVisible = isCounterVisible;
    }

    public String getTitle() {
        return title;
    }

    public int getIcon() {
        return icon;
    }

    public String getCount() {
        return count;
    }

    public boolean isCounterVisible() {
        return isCounterVisible;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setCounterVisible(boolean counterVisible) {
        isCounterVisible = counterVisible;
    }

    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
}
