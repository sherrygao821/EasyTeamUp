package com.example.easyteamup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_GENDER = "GENDER";

    public DatabaseHelper(@Nullable Context context) {
        super(context, "users.db", null, 1);
    }

    //called when accessed for the first time
    //creating new DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_GENDER + " INT)";
    }
    //call when updated/version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //add user to db
    public boolean addOne(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME,user.getFirstName()+" "+user.getLastName());
        cv.put(COLUMN_AGE,user.getAge());
        cv.put(COLUMN_GENDER,user.getGender());

        //-1 if failed to insert
        long insert = db.insert(USER_TABLE,null, cv);

        if (insert == -1) return false;
        return true;
    }
}
