package com.example.uli2.userprofilemgmt.DBHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by uli on 09/08/17.
 */

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "userprofilemgmt";

    protected static final String TABLE_APPUTIL = "AppUtilization";

    public static final String APPUTIL_ID = "id";
    public static final String APPUTIL_LABEL = "label";
    public static final String APPUTIL_ACTUAL = "value";
    public static final String APPUTIL_REGISTERED = "value2";
    public static final String APPUTIL_UTIL = "value3";


    public SQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_APPUTIL_TABLE = "CREATE TABLE " + TABLE_APPUTIL + "("
                + APPUTIL_ID + " INTEGER PRIMARY KEY," + APPUTIL_LABEL + " TEXT,"
                + APPUTIL_ACTUAL + " TEXT, " + APPUTIL_REGISTERED + " TEXT," + APPUTIL_UTIL +
                " TEXT)";
        db.execSQL(CREATE_APPUTIL_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPUTIL);
        onCreate(db);
    }
}
