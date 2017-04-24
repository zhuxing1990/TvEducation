package com.vunke.education.db;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by zhuxi on 2017/4/13.
 */
public class WatchHistryProvider extends ContentProvider {
    private static final String TAG = "WatchHistryProvider";
    private final static String AUTHORITH = "com.vunke.education.provider";
    private final static String PATH = "/watch_histry";
    private final static String PATHS = "/watch_histry/#";

    private final static String TABLE_NAME = "watch_histry";
    private final static UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_DIR = 1;
    private static final int CODE_ITEM = 2;

    static {
        mUriMatcher.addURI(AUTHORITH,PATH,CODE_DIR);
        mUriMatcher.addURI(AUTHORITH,PATH,CODE_ITEM);
    }

    private WatchHistirySQL dbHelper;
    private SQLiteDatabase db;


    @Override
    public boolean onCreate() {
        dbHelper = new WatchHistirySQL(getContext());
        return false;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)) {
            case CODE_DIR:
                return "vnd.android.cursor.dir/watch_histry";
            case CODE_ITEM:
                return "vnd.android.cursor.item/watch_histry";
            default:
                throw new IllegalArgumentException("异常参数");
        }
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        db = dbHelper.getWritableDatabase();
        //插入数据
        switch (mUriMatcher.match(uri)) {
            case 1:
                long c = db.insert(TABLE_NAME, null, values);
                Log.i(TAG, "insert: insert success:"+c);
                break;
        }
        // db.execSQL("delete from groupinfo where rowid not in(select max(rowid) from groupinfo group by user_id)");
        return uri;

    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int number = 0;
        db = dbHelper.getWritableDatabase();
        number = db.delete(TABLE_NAME, selection, selectionArgs);
        return number;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int number = 0;
        db = dbHelper.getWritableDatabase();
        number = db.update(TABLE_NAME, values, selection, selectionArgs);
        return number;
    }
}
