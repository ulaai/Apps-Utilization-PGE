package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by uli on 18/08/17.
 */

@Dao
public interface TopUsersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addTopUsers(TopUsers topUsers);

    @Query("select * from topUsers")
    public List<TopUsers> getAllTopUsers();

    @Query("select * from topUsers where id = :id")
    public List<TopUsers> getTask(long id);

    @Query("select count(*) from TopUsers")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateTopUsers(TopUsers topUsers);

    @Query("delete from topUsers")
    void removeAllTopUsers();


}
