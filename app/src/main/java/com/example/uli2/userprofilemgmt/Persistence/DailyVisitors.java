package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.util.List;

/**
 * Created by uli on 18/08/17.
 */

@Entity
public class DailyVisitors {
    @PrimaryKey
    public final long id;
    public String labels;
    public String values;

    public DailyVisitors(long id, String labels, String values) {
        this.id = id;
        this.labels = labels;
        this.values = values;
    }

    public static DailyVisitors.DailyVisitorsBuilder builder() { return new DailyVisitors.DailyVisitorsBuilder(); }

    public static class DailyVisitorsBuilder {
        private long id;
        public String labels;
        public String values;

        public DailyVisitors.DailyVisitorsBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public DailyVisitors.DailyVisitorsBuilder setLabels(String labels) {
            this.labels = labels;
            return this;
        }

        public DailyVisitors.DailyVisitorsBuilder setValues(String value) {
            this.values = value;
            return this;
        }

        public DailyVisitors build() { return new DailyVisitors(id, labels, values); }

    }

}
