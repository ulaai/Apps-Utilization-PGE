package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 11/08/17.
 */

@Entity
public class DailyPie {
    @PrimaryKey
    public final long id;
    public String high;
    public String medium;
    public String low;
    public String average;
    public String date;
    public String visitor;

    private static int instancecounter = 0;
    public int counter;

    public DailyPie(long id, String high, String medium, String low, String average, String
            visitor, String date) {
        this.id = instancecounter;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.average = average;
        this.visitor = visitor;
        this.date = date;
        instancecounter++;
    }

    public String getAttribute(int position) {
        String attribute;

        switch(position) {
            case 0:
                return this.high;
            case 1:
                return this.medium;
            default:
                return this.low;
        }

    }

    public static DailyPie.DailyBuilder builder() { return new DailyPie.DailyBuilder(); }

    public static class DailyBuilder {
        private long id;
        public String date;
        public String high;
        public String medium;
        public String low;
        public String average;
        public String visitor;


        public DailyPie.DailyBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DailyPie.DailyBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public DailyPie.DailyBuilder setHigh(String high) {
            this.high = high;
            return this;
        }

        public DailyPie.DailyBuilder setMedium(String medium) {
            this.medium = medium;
            return this;
        }

        public DailyPie.DailyBuilder setLow(String low) {
            this.low = low;
            return this;
        }

        public DailyPie.DailyBuilder setAverage(String average) {
            this.average = average;
            return this;
        }


        public DailyPie.DailyBuilder setVisitor(String visitor) {
            this.visitor = visitor;
            return this;
        }

        public DailyPie build() { return new DailyPie(id, high, medium, low, average,
                visitor, date); }

    }

}
