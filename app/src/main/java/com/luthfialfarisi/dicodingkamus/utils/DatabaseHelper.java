package com.luthfialfarisi.dicodingkamus.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.provider.BaseColumns._ID;
import static com.luthfialfarisi.dicodingkamus.utils.DatabaseContract.KamusColumns.*;
import static com.luthfialfarisi.dicodingkamus.utils.DatabaseContract.TABLE_EN_ID;
import static com.luthfialfarisi.dicodingkamus.utils.DatabaseContract.TABLE_ID_EN;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static String DATABASE_NAME = "DB_KAMUS";
    private static final int DATABASE_VERSION = 1;

    public static String CREATE_TABLE_EN_ID = "create table "+ TABLE_EN_ID + " ("+ _ID +" integer primary key autoincrement, " + WORD +" text not null, " + DESC +" text not null);";

    public static String CREATE_TABLE_ID_EN = "create table "+ TABLE_ID_EN + " ("+ _ID +" integer primary key autoincrement, " + WORD +" text not null, " + DESC + " text not null);";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_EN_ID);
        db.execSQL(CREATE_TABLE_ID_EN);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_EN_ID);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_ID_EN);
        onCreate(db);
    }
}
