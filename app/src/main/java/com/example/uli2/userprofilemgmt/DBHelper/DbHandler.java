package com.example.uli2.userprofilemgmt.DBHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by uli on 09/08/17.
 */

public class DbHandler {

    private SQLiteHelper dbHelper;

    public DbHandler(Context context) {
        dbHelper = new SQLiteHelper(context);
    }

    public int addAppData(String data0, String data1, String data2, String data3) {

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(dbHelper.APPUTIL_LABEL, String.valueOf(data0));
        values.put(dbHelper.APPUTIL_ACTUAL, String.valueOf(data1));
        values.put(dbHelper.APPUTIL_REGISTERED, String.valueOf(data2));
        values.put(dbHelper.APPUTIL_UTIL, String.valueOf(data2));

        long insertId = db.insert(dbHelper.TABLE_APPUTIL, null, values);
        db.close();
        return (int)insertId;
    }

   List<String> getAppData (int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.query(dbHelper.TABLE_APPUTIL, new String[] { dbHelper.APPUTIL_ID,
                        dbHelper.APPUTIL_LABEL, dbHelper.APPUTIL_ACTUAL, dbHelper
                        .APPUTIL_REGISTERED, dbHelper.APPUTIL_UTIL }, dbHelper
                        .APPUTIL_ID +
                        "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        List<String> result = new ArrayList<>();
        result.add(cursor.getString(1));
        result.add(cursor.getString(2));
        result.add(cursor.getString(3));

        return result;
    }


}
