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
public interface DailyVisitorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDailyVisitors(DailyVisitors dailyVisitors);

    @Query("select * from dailyVisitors")
    public List<DailyVisitors> getAllDailyVisitors();

    @Query("select * from dailyVisitors where id = :id")
    public DailyVisitors getVisitor(long id);

    @Query("select count(*) from dailyVisitors")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDailyVisitors(DailyVisitors dailyVisitors);

    @Query("delete from dailyVisitors")
    void removeAllDailyVisitors();

}
