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
public interface MonthlyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addMonthly(Monthly monthly);

    @Query("select * from monthly")
    public List<Monthly> getAllMonthly();

    @Query("select * from monthly where id = :id")
    public List<Monthly> getMonthly(long id);

    @Query("select * from monthly where date = :date")
    public List<Monthly> getMonthlyDate(String date);

    @Query("select count(*) from monthly")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMonthly(Monthly monthly);

    @Query("delete from monthly")
    void removeAllMonthly();

}
