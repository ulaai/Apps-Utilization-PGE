package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by uli on 11/08/17.
 */

@Dao
public interface MonthlyPieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMonthlyPie(MonthlyPie MonthlyPie);

    @Query("select * from MonthlyPie")
    public List<MonthlyPie> getAllMonthlyPie();

    @Query("select * from MonthlyPie where id = :id")
    public MonthlyPie getMonthlyPie(long id);

    @Query("select * from MonthlyPie where date = :date")
    public List<MonthlyPie> getPieDate(String date);

    @Query("select count(*) from MonthlyPie where date = :date")
    public int getCountDate(String date);

    @Query("select count(*) from MonthlyPie")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMonthlyPie(MonthlyPie MonthlyPie);

    @Query("delete from MonthlyPie")
    void removeAllMonthlyPie();

}
