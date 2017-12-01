package com.sayem.geeknot.taskscheduler.database;

/**
 * Created by Sayem on 1/12/2017.
 */

import android.provider.BaseColumns;

public class Data {
    public static final String DB_NAME = "com.sayem.geeknot.taskscheduler.database.db";
    public static final int DB_VERSION= 1;

    public class DataEntry implements BaseColumns{
        public static final String TABLE="TaskSched";
        public static final String COL_TASK_TITLE="title";
        public static final String COL_TASK_DATE = "date";
        public static final String COL_TIMESTAMP = "timestamp";

    }
}