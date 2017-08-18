package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 18/08/17.
 */

@Entity
public class AnnuallyVisitors {
    @PrimaryKey
    public final long id;
    public String labels;
    public String values;

    public AnnuallyVisitors(long id, String labels, String values) {
        this.id = id;
        this.labels = labels;
        this.values = values;
    }

    public static AnnuallyVisitors.AnnuallyVisitorsBuilder builder() { return new AnnuallyVisitors.AnnuallyVisitorsBuilder(); }

    public static class AnnuallyVisitorsBuilder {
        private long id;
        public String labels;
        public String values;

        public AnnuallyVisitors.AnnuallyVisitorsBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public AnnuallyVisitors.AnnuallyVisitorsBuilder setLabels(String labels) {
            this.labels = labels;
            return this;
        }

        public AnnuallyVisitors.AnnuallyVisitorsBuilder setValues(String value) {
            this.values = value;
            return this;
        }

        public AnnuallyVisitors build() { return new AnnuallyVisitors(id, labels, values); }

    }

}
