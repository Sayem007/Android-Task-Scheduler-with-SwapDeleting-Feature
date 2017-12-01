package com.sayem.geeknot.taskscheduler.database;

/**
 * Created by Sayem on 1/12/2017.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataHelper extends SQLiteOpenHelper {

    public DataHelper(Context context) {
        super(context, Data.DB_NAME, null, Data.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable = "CREATE TABLE " + Data.DataEntry.TABLE + " (" +
                Data.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                Data.DataEntry.COL_TASK_TITLE + " TEXT NOT NULL," +
                Data.DataEntry.COL_TASK_DATE + " INTEGER NOT NULL," +
                Data.DataEntry.COL_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Data.DataEntry.TABLE);
        onCreate(db);
    }
}
