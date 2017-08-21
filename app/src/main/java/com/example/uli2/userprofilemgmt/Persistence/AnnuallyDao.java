package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by uli on 10/08/17.
 */

@Dao
public interface AnnuallyDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnnually(Annually annually);

    @Query("select * from annually")
    public List<Annually> getAllAnnually();

    @Query("select * from annually where id = :id")
    public Annually getTask(long id);

    @Query("select * from annually where date = :date")
    public List<Annually> getTaskDate(String date);

    @Query("select utilization from annually")
    public List<String> getUtil();

    @Query("select count(*) from annually")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnnually(Annually annually);

    @Query("delete from annually")
    void removeAllAnnually();
}
