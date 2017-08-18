package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 18/08/17.
 */

@Entity
public class TopUsers {
    @PrimaryKey
    public final long id;
    public String label;
    public String value;

    public TopUsers(long id, String label, String value) {
        this.id = id;
        this.label = label;
        this.value = value;
    }

    public static TopUsers.TopUsersBuilder builder() { return new TopUsers.TopUsersBuilder(); }

    public static class TopUsersBuilder {
        private long id;
        public String label;
        public String value;

        public TopUsers.TopUsersBuilder setId(long id) {
            this.id = id;
            return this;
        }

        public TopUsers.TopUsersBuilder setLabel(String label) {
            this.label = label;
            return this;
        }

        public TopUsers.TopUsersBuilder setValue(String value) {
            this.value = value;
            return this;
        }

        public TopUsers build() { return new TopUsers(id, label, value); }

    }


}
