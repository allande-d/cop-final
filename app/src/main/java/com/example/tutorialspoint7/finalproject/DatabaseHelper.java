package com.example.tutorialspoint7.finalproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
//Declaring variables for this class
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
    public static  String hold_id = "";
    public static Integer hold_count = 0;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    //Once called this function initailizes the DB with its columns
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT,NAME TEXT,EMAIL TEXT,PASSWORD TEXT,MOVIEID1 TEXT," +
                "MOVIEID2 TEXT,MOVIEID3 TEXT,MOVIEID4 TEXT,COUNT INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
//Function used to insert into DB. Takes a name, email and password from EDITTEXT string
    public boolean insertData(String name,String username,String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2,name);
        contentValues.put(COL_3,username);
        contentValues.put(COL_4,password);
        contentValues.put(COL_5,"");
        contentValues.put(COL_6,"");
        contentValues.put(COL_7,"");
        contentValues.put(COL_8,"");
        contentValues.put(COL_9,0);
        long result = db.insert(TABLE_NAME,null ,contentValues);
        if(result == -1)
            return false;
        else
            return true;
    }

//Called to put a movieID into one of the columns depending on count
    public void insertMovieId(String movie_id, String user, Integer count) {
        SQLiteDatabase db = this.getWritableDatabase();
        //If count is zero the MOVIEID is added to the column MOVIEID1
        if(count==0)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_5, movie_id);
            //Update query
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        //If count is zero the MOVIEID is added to the column MOVIEID2
        if(count==1)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_6, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        //If count is zero the MOVIEID is added to the column MOVIEID3
        if(count==2)
        {
            ContentValues newValues = new ContentValues();
            newValues.put(COL_7, movie_id);
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        //If count is zero the MOVIEID is added to the column MOVIEID4
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
    //Function returns an integer with the count value for the provided email
    public Integer getCount(String user){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{user});
        //Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{check_user} ,null);
        res.moveToFirst();
        return res.getInt(8);
    }
    //Increments count value for the user
    public void incrementCount(Integer count, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COL_9, count);
        db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
        }
        //Function returns a string containing the MOVIEID for the user
    public String getID(String user, Integer column){
        //Select query
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{user});
        //Cursor res = db.rawQuery("select * from "+ TABLE_NAME + " WHERE " + COL_3+ " =?", new String[]{check_user} ,null);
        res.moveToFirst();
        return res.getString(column);
    }
    public void deleteMovie(String user, Integer column){
        hold_count = getCount(user);
        if(column == 1) {
            //COL_5 - Delete version if the first movie is the one the user wants to delete
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(COL_5, getID(user,7));
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
            decrementCount(getCount(user),user);
        }
        if(column == 2) {
            //COL_6 - Delete version if the second movie is the one the user wants to delete
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(COL_6, getID(user,7));
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
            decrementCount(getCount(user),user);
        }
        if(column == 3) {
            //COL_7 - Delete version if the third movie is the one the user wants to delete
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(COL_7, getID(user,7));
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
            decrementCount(getCount(user),user);
        }
        if(column == 4) {
            //COL_8 - Delete version if the fourth movie is the one the user wants to delete
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues newValues = new ContentValues();
            newValues.put(COL_8, "");
            db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
            decrementCount(getCount(user),user);
        }
        //Gets passed column of movie being deleted
        //Checks position of the column being deleted if its movie1,movie2.....
        //Makes the value of the column being deleted equal to null
        // Moves every movie ID to the right of it to the left

    }
    //Function to decrement the count value. Takes a count number and a email
    public void decrementCount(Integer count, String user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues newValues = new ContentValues();
        newValues.put(COL_9, count-1);
        db.update(TABLE_NAME, newValues, String.format("%s = ?", COL_3), new String[]{user});
    }
}