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
public interface AnnuallyPieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addAnnuallyPie(AnnuallyPie AnnuallyPie);

    @Query("select * from AnnuallyPie")
    public List<AnnuallyPie> getAllAnnuallyPie();

    @Query("select * from AnnuallyPie where id = :id")
    public AnnuallyPie getAnnuallyPie(long id);

    @Query("select * from AnnuallyPie where date = :date")
    public List<AnnuallyPie> getTaskDate(String date);

    @Query("select count(*) from AnnuallyPie")
    public int getCount();

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateAnnuallyPie(AnnuallyPie AnnuallyPie);

    @Query("delete from AnnuallyPie")
    void removeAllAnnuallyPie();

}
