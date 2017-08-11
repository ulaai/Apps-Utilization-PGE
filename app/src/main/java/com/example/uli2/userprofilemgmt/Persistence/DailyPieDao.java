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
public interface DailyPieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDailyPie(DailyPie DailyPie);

    @Query("select * from DailyPie")
    public List<DailyPie> getAllDailyPie();

    @Query("select * from DailyPie where id = :id")
    public List<DailyPie> getPie(long id);

    @Query("select * from DailyPie where date = :date")
    public List<DailyPie> getPieDate(String date);

    @Query("select count(*) from DailyPie where date = :date")
    public int getCountDate(String date);

    @Query("select count(*) from DailyPie")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDailyPie(DailyPie DailyPie);

    @Query("delete from DailyPie")
    void removeAllDailyPie();

}
