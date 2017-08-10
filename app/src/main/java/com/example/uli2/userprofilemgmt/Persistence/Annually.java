package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uli on 10/08/17.
 */
@Entity
public class Annually {
    @PrimaryKey
    public final long id;
    public String label;
    public String actual;
    public String registered;
    public String utilization;
    public String date;

    public String high;
    public String medium;
    public String low;
    public String average;

    public Annually(long id, String label, String actual, String registered, String utilization,
                    String date, String high, String medium, String low, String average) {
        this.id = id;
        this.label = label;
        this.actual = actual;
        this.registered = registered;
        this.utilization = utilization;
        this.date = date;
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.average = average;
    }

    public static AnnualBuilder builder() { return new AnnualBuilder(); }

    public static class AnnualBuilder {
        private long id;
        public String label;
        public String actual;
        public String registered;
        public String utilization;
        public String date;
        public String high;
        public String medium;
        public String low;
        public String average;


        public AnnualBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AnnualBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public AnnualBuilder setActual(String actual) {
            this.actual = actual;
            return this;
        }

        public AnnualBuilder setRegistered(String registered) {
            this.registered = registered;
            return this;
        }

        public AnnualBuilder setUtilization(String utilization) {
            this.utilization = utilization;
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

        public Annually build() { return new Annually(id, label, actual, registered, utilization,
                date, high, medium, low, average); }

    }


    public void setAnnualPie(String high, String medium, String low, String average) {
        this.high = high;
        this.medium = medium;
        this.low = low;
        this.average = average;
    }

    @Override
    public String toString() {
        return "Task{" +
                "label=" + label +
                ", actual='" + actual + '\'' +
                ", reg='" + registered + '\'' +
                ", util=" + utilization +
                '}';
    }

}
