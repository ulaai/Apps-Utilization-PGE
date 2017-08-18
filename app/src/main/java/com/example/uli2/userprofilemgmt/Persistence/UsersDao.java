package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by uli on 16/08/17.
 */

@Dao
public interface UsersDao {
	@Insert(onConflict = OnConflictStrategy.REPLACE)
    void addUsers(Users users);

    @Query("select * from users")
    public List<Users> getAllUsers();

    @Query("select * from users where userName like :userName")
    public List<Users> getUsers(String userName);

    @Query("select * from users where userName like :userName")
    public List<Users> getUsersDisplay(String userName);

    @Query("select * from users where id = :id")
    public List<Users> getUserId(long id);

    @Query("select * from users where loggedIn = 1")
    public Users getLoggedInUser();

    @Query("update users set loggedIn = 1 where id = :id")
    public void setLoggedInUser(long id);

    @Query("select count(*) from users")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateUsers(Users users);

    @Query("delete from users")
    void removeAllUsers();
}
