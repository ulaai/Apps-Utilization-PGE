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
public interface MonthlyVisitorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMonthlyVisitors(MonthlyVisitors monthlyVisitors);

    @Query("select * from monthlyVisitors")
    public List<MonthlyVisitors> getAllMonthlyVisitors();

    @Query("select * from monthlyVisitors where id = :id")
    public MonthlyVisitors getVisitor(long id);

    @Query("select count(*) from monthlyVisitors")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMonthlyVisitors(MonthlyVisitors monthlyVisitors);

    @Query("delete from monthlyVisitors")
    void removeAllMonthlyVisitors();
}
