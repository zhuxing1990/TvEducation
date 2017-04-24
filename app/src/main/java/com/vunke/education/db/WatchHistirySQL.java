package com.vunke.education.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by zhuxi on 2017/4/13.
 */
public class WatchHistirySQL extends SQLiteOpenHelper {
    private  final static String DATABASE_NAME = "watch_histiry.db";
    private  final static int DATABASE_VESITION = 1;
    public WatchHistirySQL(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VESITION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table watch_histiry (_id integer not null primary key autoincrement,jsondata varchar,user_id varchar,createtime varchar");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String sql = "drop table if exists watch_histiry";
        db.execSQL(sql);
    }
}
