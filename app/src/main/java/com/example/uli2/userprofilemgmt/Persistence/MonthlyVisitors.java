package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 18/08/17.
 */

@Entity
public class MonthlyVisitors {
    @PrimaryKey
    public final long id;
    public String labels;
    public String values;

    public MonthlyVisitors(long id, String labels, String values) {
        this.id = id;
        this.labels = labels;
        this.values = values;
    }

    public static MonthlyVisitors.MonthlyVisitorsBuilder builder() { return new MonthlyVisitors.MonthlyVisitorsBuilder(); }

    public static class MonthlyVisitorsBuilder {
        private long id;
        public String labels;
        public String values;

        public MonthlyVisitors.MonthlyVisitorsBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public MonthlyVisitors.MonthlyVisitorsBuilder setLabels(String labels) {
            this.labels = labels;
            return this;
        }

        public MonthlyVisitors.MonthlyVisitorsBuilder setValues(String value) {
            this.values = value;
            return this;
        }

        public MonthlyVisitors build() { return new MonthlyVisitors(id, labels, values); }

    }

}
