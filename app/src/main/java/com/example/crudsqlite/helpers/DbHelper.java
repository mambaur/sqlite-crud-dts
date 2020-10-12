package com.example.crudsqlite.helpers;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class DbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 2;
    static final String DATABASE_NAME = "digitaltalent.db";
    public static final String TABLE_SQLITE = "sqlite";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ADDRESS = "address";

    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public ArrayList<HashMap<String, String>> getAllData(){
        ArrayList<HashMap<String, String>> wordList;
        wordList =  new ArrayList<HashMap<String, String>>();
        String selectQuery = "SELECT * FROM "+ TABLE_SQLITE;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do{
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(COLUMN_ID, cursor.getString(0));
                map.put(COLUMN_NAME, cursor.getString(1));
                map.put(COLUMN_ADDRESS, cursor.getString(2));
                wordList.add(map);
            }while(cursor.moveToNext());
        }
        Log.e("select sqlite ", "" + wordList);

        db.close();
        return wordList;
    }

    public void insert(String name, String address){
        SQLiteDatabase database = this.getWritableDatabase();
        String queryValues = "INSERT INTO " + TABLE_SQLITE + " (name, address) " +
                "VALUES ('" + name + "', '" + address + "')";

        Log.e("insert sqlite ", "" + queryValues);
        database.execSQL(queryValues);
        database.close();
    }

    public void update(int id, String name, String address){
        SQLiteDatabase db = this.getWritableDatabase();

        String updateQuery = "UPDATE " + TABLE_SQLITE + " SET " +
                COLUMN_NAME + "='" + name +"', " +
                COLUMN_ADDRESS + "='" + address + "'" +
                " WHERE " + COLUMN_ID + "='" + id + "'";
        Log.e("upadate sqlite ", updateQuery);
        db.execSQL(updateQuery);
        db.close();
    }

    public void delete(int id){
        SQLiteDatabase db =  this.getWritableDatabase();
        String deleteQuery = "DELETE FROM " + TABLE_SQLITE + " WHERE " + COLUMN_ID + "='" + id + "'";
        db.execSQL(deleteQuery);
        db.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
