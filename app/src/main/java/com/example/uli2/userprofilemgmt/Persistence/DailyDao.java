package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by uli on 14/08/17.
 */

@Dao
public interface DailyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addDaily(Daily daily);

    @Query("select * from daily")
    public List<Daily> getAllDaily();

    @Query("select * from daily where id = :id")
    public List<Daily> getDaily(long id);

    @Query("select * from daily where date = :date")
    public List<Daily> getDailyDate(String date);

    @Query("select count(*) from daily")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateDaily(Daily daily);

    @Query("delete from daily")
    void removeAllDaily();

}
