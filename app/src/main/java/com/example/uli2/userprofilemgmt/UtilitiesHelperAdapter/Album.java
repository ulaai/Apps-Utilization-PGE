package com.example.uli2.userprofilemgmt.UtilitiesHelperAdapter;

/**
 * Created by user on 7/24/2017.
 */

public class Album {
    private String name;
    private int thumbnail;
    private String chart;
    private String fragment;

    public Album() {
    }

    public Album(String name, int thumbnail, String chart, String fragment) {
        this.name = name;
        this.thumbnail = thumbnail;
        this.chart = chart;
        this.fragment = fragment;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getChart() {
        return chart;
    }

    public void setChart(String chart) {
        this.chart = chart;
    }

    public String getFragment() {
        return fragment;
    }

    public void setFragment(String fragment) {
        this.fragment = fragment;
    }
}
