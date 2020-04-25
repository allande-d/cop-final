package com.example.tutorialspoint7.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Users.db";
    public static final String TABLE_NAME = "accounts_table";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PASSWORD";
    public static final String COL_5 = "MOVIEID1";
    public static final String COL_6 = "MOVIEID2";
    public static final String COL_7 = "MOVIEID3";
    public static final String COL_8 = "MOVIEID4";
    public static final String COL_9 = "COUNT";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,MOVIEID1 TEXT," +
                "MOVIEID2 TEXT,MOVIEID3 TEXT,MOVIEID4 TEXT,COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }

    public boolean insertData(String name,String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,username);
        contentValues.put(COL_4,password);
        contentValues.put(COL_9,0);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }


    public void insertMovieId(String movie_id, String user, Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("UPDATE " + TABLE_NAME + " SET " + COL_5 + " =?" + movie_id + " WHERE " + COL_3 + "=?", new String[]{user});
        if(count==0)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_5, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        if(count==1)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_6, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        if(count==2)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_7, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        if(count==3)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_8, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public String getAllData(String check_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{check_user});
        //Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{check_user} ,null);
        res.moveToFirst();
        return res.getString(3);
    }
    public Integer getCount(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{user});
        //Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{check_user} ,null);
        res.moveToFirst();
        return res.getInt(8);
    }
    public void incrementCount(Integer count, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COL_9, count);
        db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
}