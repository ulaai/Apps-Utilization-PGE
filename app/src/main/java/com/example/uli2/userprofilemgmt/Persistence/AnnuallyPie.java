package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 10/08/17.
 */

@Entity
public class AnnuallyPie {
    @PrimaryKey
    public final long id;
    public String high;
    public String medium;
    public String low;
    public String average;
    public String date;
    public String visitor;

    public AnnuallyPie(long id, String high, String medium, String low, String average, String
            visitor, String date) {
        this.id = id;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.average = average;
        this.visitor = visitor;
        this.date = date;
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

    public String getAverage() {
        return this.average;
    }

    public static AnnualBuilder builder() { return new AnnualBuilder(); }

    public static class AnnualBuilder {
        private long id;
        public String date;
        public String high;
        public String medium;
        public String low;
        public String average;
        public String visitor;


        public AnnualBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AnnualBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public AnnualBuilder setHigh(String high) {
            this.high = high;
            return this;
        }

        public AnnualBuilder setMedium(String medium) {
            this.medium = medium;
            return this;
        }

        public AnnualBuilder setLow(String low) {
            this.low = low;
            return this;
        }

        public AnnualBuilder setAverage(String average) {
            this.average = average;
            return this;
        }


        public AnnualBuilder setVisitor(String visitor) {
            this.visitor = visitor;
            return this;
        }

        public AnnuallyPie build() { return new AnnuallyPie(id, high, medium, low, average,
                visitor, date); }

    }


}
