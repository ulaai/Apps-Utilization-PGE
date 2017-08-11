package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 11/08/17.
 */

@Entity
public class MonthlyPie {
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

    public MonthlyPie(long id, String high, String medium, String low, String average, String
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
                return this.average;
            case 1:
                return this.high;
            case 2:
                return this.medium;
            default:
                return this.low;
        }

    }

    public static MonthlyBuilder builder() { return new MonthlyBuilder(); }

    public static class MonthlyBuilder {
        private long id;
        public String date;
        public String high;
        public String medium;
        public String low;
        public String average;
        public String visitor;


        public MonthlyBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public MonthlyBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public MonthlyBuilder setHigh(String high) {
            this.high = high;
            return this;
        }

        public MonthlyBuilder setMedium(String medium) {
            this.medium = medium;
            return this;
        }

        public MonthlyBuilder setLow(String low) {
            this.low = low;
            return this;
        }

        public MonthlyBuilder setAverage(String average) {
            this.average = average;
            return this;
        }


        public MonthlyBuilder setVisitor(String visitor) {
            this.visitor = visitor;
            return this;
        }

        public MonthlyPie build() { return new MonthlyPie(id, high, medium, low, average,
                visitor, date); }

    }

}
