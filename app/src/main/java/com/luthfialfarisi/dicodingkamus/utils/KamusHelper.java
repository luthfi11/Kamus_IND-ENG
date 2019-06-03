package com.luthfialfarisi.dicodingkamus.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import java.util.ArrayList;

import com.luthfialfarisi.dicodingkamus.models.KamusItems;

import static android.provider.BaseColumns._ID;
import static com.luthfialfarisi.dicodingkamus.utils.DatabaseContract.KamusColumns.*;

public class KamusHelper {

    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public KamusHelper(Context context){
        this.context = context;
    }

    public KamusHelper open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dbHelper.close();
    }

    public ArrayList<KamusItems> getDataByWord(String keyword, String language){

        Cursor cursor;
        cursor = database.query(language,null,WORD+" LIKE ?",new String[]{keyword.trim()+"%"},null,null,_ID + " ASC",null);
        cursor.moveToFirst();

        ArrayList<KamusItems> arrayList = new ArrayList<>();
        KamusItems KamusItems;
        if (cursor.getCount()>0) {
            do {
                KamusItems = new KamusItems();
                KamusItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                KamusItems.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                KamusItems.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));

                arrayList.add(KamusItems);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<KamusItems> getAllData(String language){
        Cursor cursor;

        cursor = database.query(language,null,null,null,null,null,_ID+ " ASC",null);

        cursor.moveToFirst();

        ArrayList<KamusItems> arrayList = new ArrayList<>();
        KamusItems kamusItems;
        if (cursor.getCount()>0) {
            do {
                kamusItems = new KamusItems();
                kamusItems.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                kamusItems.setWord(cursor.getString(cursor.getColumnIndexOrThrow(WORD)));
                kamusItems.setDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESC)));

                arrayList.add(kamusItems);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(KamusItems kamusItems, String table){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(WORD, kamusItems.getWord());
        initialValues.put(DESC, kamusItems.getDesc());
        return database.insert(table, null, initialValues);
    }

    public void beginTransaction(){
        database.beginTransaction();
    }

    public void setTransactionSuccess(){
        database.setTransactionSuccessful();
    }

    public void endTransaction(){
        database.endTransaction();
    }

    public void insertTransaction(KamusItems kamusItems, String table){
        String sql = "INSERT INTO "+table+" ("+WORD+", "+DESC
                +") VALUES (?, ?)";
        SQLiteStatement stmt = database.compileStatement(sql);
        stmt.bindString(1, kamusItems.getWord());
        stmt.bindString(2, kamusItems.getDesc());
        stmt.execute();
        stmt.clearBindings();
    }

    public int update(KamusItems kamusItems, String table){
        ContentValues args = new ContentValues();
        args.put(WORD, kamusItems.getWord());
        args.put(DESC, kamusItems.getDesc());
        return database.update(table, args, _ID + "= '" + kamusItems.getId() + "'", null);
    }

    public int delete(int id, String table){
        return database.delete(table, _ID + " = '"+id+"'", null);
    }
}
