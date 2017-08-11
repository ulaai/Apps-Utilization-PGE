package com.example.uli2.userprofilemgmt.Persistence;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;

/**
 * Created by uli on 10/08/17.
 */

@Database(entities = {Annually.class, AnnuallyPie.class, MonthlyPie.class, DailyPie.class},
        version = 4,
        exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    public abstract AnnuallyDao annuallyModel();

    public abstract AnnuallyPieDao annuallyPieModel();
    public abstract MonthlyPieDao monthlyPieModel();
    public abstract DailyPieDao dailyPieModel();

    public static AppDatabase getDatabase(Context context) {
        if(INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "apputilization")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries().build();
        }

        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `MonthlyPie` (`id` INTEGER, `high` TEXT, `medium` " +
                    "TEXT, " + "`low` TEXT, `average` TEXT, `date` TEXT, `visitor` TEXT, PRIMARY KEY(`id`))");
            database.execSQL("CREATE TABLE `DailyPie` (`id` INTEGER, `high` TEXT, `medium` " +
                    "TEXT, " + "`low` TEXT, `average` TEXT, `date` TEXT, `visitor` TEXT, PRIMARY KEY(`id`))");
        }
    };

}
