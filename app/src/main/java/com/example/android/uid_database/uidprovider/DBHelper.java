package com.example.android.uid_database.uidprovider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.uid_database.uidprovider.UidContract.*;


public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "uid.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private String SQL_CreateUidTable(){
        String SQL_CREATE_TABLE = "CREATE TABLE " + UidStore.TABLE_NAME + " (" +
                UidStore._ID + " INTEGER PRIMARY KEY," +
                UidStore.COLUMN_DATE + " TEXT," +
                UidStore.COLUMN_UID + " TEXT NOT NULL UNIQUE ON CONFLICT IGNORE," +
                UidStore.COLUMN_PW + " TEXT NOT NULL" + ");";
        return SQL_CREATE_TABLE;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CreateUidTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + UidStore.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
