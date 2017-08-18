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
public interface AnnuallyVisitorsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnnuallyVisitors(AnnuallyVisitors annuallyVisitors);

    @Query("select * from annuallyVisitors")
    public List<AnnuallyVisitors> getAllAnnuallyVisitors();

    @Query("select * from annuallyVisitors where id = :id")
    public AnnuallyVisitors getVisitor(long id);

    @Query("select count(*) from annuallyVisitors")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnnuallyVisitors(AnnuallyVisitors annuallyVisitors);

    @Query("delete from annuallyVisitors")
    void removeAllAnnuallyVisitors();
}
