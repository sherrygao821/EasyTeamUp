package com.example.easyteamup;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.User;

/**
 * Author: Andy C
 *
 * Instruction:
 *
 * DatabaseHelper dbhelper = new DatabaseHelper(MainActivity.this);
 *
 * boolean success = dbhelper.addUser(User u1);
 *  OR
 * boolean sucesss = dbhelper.addEvent(Event e1);
 *
 *
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    //User Table static constants
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_STUDENT = "STUDENT";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PWD = "PWD";



    //Event Table static constants
    public static final String EVENT_TABLE = "EVENT_TABLE";
    public static final String COLUMN_EVT_NAME = "EVT_NAME";
    public static final String COLUMN_EVT_ID = "EVT_ID";
    public static final String COLUMN_HOST_ID = "HOST_ID";
    public static final String COLUMN_TIME = "TIME";
    public static final String COLUMN_LOCATION = "LOCATION";
    public static final String COLUMN_EVT_TYPE = "TYPE";
    public static final String COLUMN_TIMESLOTS = "TIMESLOTS";
    public static final String COLUMN_PARTICIPANTS = "PARTICIPANTS";

    //name: "Database.db"
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    //called when accessed for the first time
    //creating new DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PWD + " TEXT, " + COLUMN_STUDENT + " INT)";
        String createEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_NAME + " TEXT, " + COLUMN_HOST_ID + " INT, " + COLUMN_TIME + " TEXT, "  + COLUMN_LOCATION + " TEXT, " + COLUMN_TIMESLOTS + " TEXT, " + COLUMN_PARTICIPANTS + " TEXT, "  + COLUMN_EVT_TYPE + " INT)";
    }
    //call when updated/version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    //add user to db one at a time
    public boolean addUser(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_NAME, user.getFirstName()+" "+user.getLastName());
        cv.put(COLUMN_AGE, user.getAge());
        cv.put(COLUMN_STUDENT, user.getStudent() ? 1 : 0);
        cv.put(COLUMN_EMAIL, user.getEmail());
        cv.put(COLUMN_PWD, user.getUserPwd());

        //-1 if failed to insert
        long insert = db.insert(USER_TABLE,null, cv);

        if (insert == -1) return false;
        return true;
    }

    //add event to db one at a time
    public boolean addEvent (Event event){
        SQLiteDatabase db = this .getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVT_NAME, event.getEvtName());
        cv.put(COLUMN_EVT_ID, event.getEvtId());
        cv.put(COLUMN_HOST_ID, event.getHostId());
        cv.put(COLUMN_TIME, event.getEvtTime().toString());
        cv.put(COLUMN_LOCATION, event.getEvtLocation());
        cv.put(COLUMN_EVT_TYPE, event.getEvtType());
        cv.put(COLUMN_TIMESLOTS, event.getEvtTimeSlots().toString());
        cv.put(COLUMN_PARTICIPANTS, event.getEvtParticipants().toString());

        //-1 if failed to insert
        long insert = db.insert(EVENT_TABLE,null, cv);

        if (insert == -1) return false;
        return true;
    }
}
