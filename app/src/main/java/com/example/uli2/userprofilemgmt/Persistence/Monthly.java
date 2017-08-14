package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 14/08/17.
 */

@Entity
public class Monthly {
    @PrimaryKey
    public final long id;
    public String label;
    public String actual;
    public String registered;
    public String utilization;
    public String date;
    public String access;

    public Monthly(long id, String label, String actual, String registered, String utilization,
                   String date, String access) {
        this.id = id;
        this.label = label;
        this.actual = actual;
        this.registered = registered;
        this.utilization = utilization;
        this.date = date;
        this.access = access;
    }

    public static MonthlyBuilder builder() { return new MonthlyBuilder(); }

    public static class MonthlyBuilder {
        private long id;
        public String label;
        public String actual;
        public String registered;
        public String utilization;
        public String date;
        public String access;


        public MonthlyBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public MonthlyBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public MonthlyBuilder setActual(String actual) {
            this.actual = actual;
            return this;
        }

        public MonthlyBuilder setRegistered(String registered) {
            this.registered = registered;
            return this;
        }

        public MonthlyBuilder setUtilization(String utilization) {
            this.utilization = utilization;
            return this;
        }

        public MonthlyBuilder setDate(String date) {
            this.date = date;
            return this;
        }

        public MonthlyBuilder setAccess(String access) {
            this.access = access;
            return this;
        }

        public Monthly build() { return new Monthly(id, label, actual, registered, utilization,
                date, access); }
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
