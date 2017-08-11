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
    public String access;

    public Annually(long id, String label, String actual, String registered, String utilization,
                    String date, String access) {
        this.id = id;
        this.label = label;
        this.actual = actual;
        this.registered = registered;
        this.utilization = utilization;
        this.date = date;
        this.access = access;
    }

    public static AnnualBuilder builder() { return new AnnualBuilder(); }

    public static class AnnualBuilder {
        private long id;
        public String label;
        public String actual;
        public String registered;
        public String utilization;
        public String date;
        public String access;


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

        public AnnualBuilder setAccess(String access) {
            this.access = access;
            return this;
        }

        public Annually build() { return new Annually(id, label, actual, registered, utilization,
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
