package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by uli on 16/08/17.
 */

@Entity
public class Users {
    @PrimaryKey
    public final long id;
    public String userName;
    public String userDisplayName;
    public boolean loggedIn;

    public Users(long id, String userName, String userDisplayName, boolean loggedIn) {
    	this.id = id;
    	this.userName = userName;
    	this.userDisplayName = userDisplayName;
    	this.loggedIn = loggedIn;
    }

    public static UsersBuilder builder() { return new UsersBuilder(); }

    public static class UsersBuilder {
    	private long id;
    	public String userName;
    	public String userDisplayName;
    	public boolean loggedIn;

    	public UsersBuilder setId(long id) {
            this.id = id;
            return this;
        }

    	public UsersBuilder setuserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UsersBuilder setuserDisplayName(String userDisplayName) {
            this.userDisplayName = userDisplayName;
            return this;
        }

        public UsersBuilder setloggedIn(boolean loggedIn) {
            this.loggedIn = loggedIn;
            return this;
        }

        public Users build() { return new Users(id, userName, userDisplayName, loggedIn); }

    }
}
