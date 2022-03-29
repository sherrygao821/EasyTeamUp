package com.example.easyteamup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.easyteamup.classes.Event;
import com.example.easyteamup.classes.Notification;
import com.example.easyteamup.classes.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DatabaseHelper extends SQLiteOpenHelper {

    //User Table static constants
    public static final String USER_TABLE = "USER_TABLE";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_ID = "ID";
    public static final String COLUMN_AGE = "AGE";
    public static final String COLUMN_STUDENT = "STUDENT";
    public static final String COLUMN_EMAIL = "EMAIL";
    public static final String COLUMN_PWD = "PWD";
    public static final String COLUMN_PHOTO = "PHOTO";

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
    public static final String COLUMN_EVT_DURATION = "DURATION";
    public static final String COLUMN_EVT_PUBLIC = "IS_PUBLIC";
    public static final String COLUMN_EVT_DESCRIPTION = "EVT_DESCRIPTION";



    //Notification Table constants;
    public static final String NOTIFICATION_TABLE = "NOTIFICATION_TABLE";
    public static final String COLUMN_NOTIFICATION_ID = "NOTI_ID";
    public static final String COLUMN_FROM_ID = "FROM_ID";
    public static final String COLUMN_TO_ID = "TO_ID";
    public static final String COLUMN_NOTIFICATION_TYPE = "TYPE";

    //name: "Database.db"
    public DatabaseHelper(@Nullable Context context) {
        super(context, "Database.db", null, 1);
    }

    //called when accessed for the first time
    //creating new DB
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PWD + " TEXT, " + COLUMN_STUDENT + " INT)";
        String createEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_NAME + " TEXT, " + COLUMN_HOST_ID + " INT, " + COLUMN_TIME + " TEXT, "  + COLUMN_LOCATION + " TEXT, " + COLUMN_TIMESLOTS + " TEXT, " + COLUMN_PARTICIPANTS + " TEXT, " + COLUMN_EVT_DURATION + " TEXT, " + COLUMN_EVT_TYPE + " INT, " + COLUMN_EVT_DESCRIPTION + " TEXT, " + COLUMN_EVT_PUBLIC + " BOOLEAN)";
        String createNotiTableStatement = "CREATE TABLE " + NOTIFICATION_TABLE + " (" + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_ID + " INT, " + COLUMN_FROM_ID + " INT, " + COLUMN_TO_ID + " INT, " + COLUMN_NOTIFICATION_TYPE + " INT)";
        db.execSQL(createEventTableStatement);
        db.execSQL(createUserTableStatement);
        db.execSQL(createNotiTableStatement);
    }
    //call when updated/version changes
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    /**
     * Add user to db
     * @param user
     * @return boolean
     * @author Andy
     */
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

    /**
     * Check whether the user email is already in database
     * @param username
     * @return
     * @author Sherry Gao
     */
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
//        Cursor cursor = db.rawQuery("SELECT ID, PHOTO FROM USER_TABLE WHERE EMAIL = ?", new String[] {username});
        Cursor cursor = db.rawQuery("SELECT ID FROM USER_TABLE WHERE EMAIL = ?", new String[] {username});
        if(cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    /**
     * Check whether user email and password matches
     * @param username
     * @param password
     * @return
     * @author Sherry Gao
     */
    public Cursor checkUsernamePassword(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_TABLE WHERE EMAIL = ? AND PWD = ?", new String[] {username, password});
        if (cursor.moveToFirst())
            return cursor;
        else
            return null;
    }

    /**
     * Add event to db one at a time
     * @param event
     * @param invitees
     * @return boolean
     * @author Andy / Sherry Gao
     */
    public boolean addEvent (Event event, List<String> invitees){

        Log.d("DATABASE", new Gson().toJson(event));
        Log.d("DATABASE", new Gson().toJson(invitees));

        SQLiteDatabase db = this .getWritableDatabase();
        ContentValues cv = new ContentValues();

        String participantsString = new Gson().toJson(event.getEvtParticipants());
        String timeSlotsString = new Gson().toJson(event.getEvtTimeSlots());

        cv.put(COLUMN_EVT_NAME, event.getEvtName());
        // TODO: SEND NOTI TO INVITEES
        cv.put(COLUMN_HOST_ID, String.valueOf(event.getHostId()));
        cv.put(COLUMN_TIME, event.getEvtSignUpDueDate());
        cv.put(COLUMN_LOCATION, event.getEvtLocation());
        cv.put(COLUMN_EVT_TYPE, String.valueOf(event.getEvtType()));
        cv.put(COLUMN_TIMESLOTS, timeSlotsString);
        cv.put(COLUMN_PARTICIPANTS, participantsString);
        cv.put(COLUMN_EVT_DURATION, event.getEvtDuration());
        cv.put(COLUMN_EVT_PUBLIC, event.isPublic());
        cv.put(COLUMN_EVT_DESCRIPTION, event.getEvtDescription());

        //-1 if failed to insert
        long insert = db.insert(EVENT_TABLE,null, cv);

        if (insert == -1) return false;
        return true;
    }

    /**
     * get name of a user with userid
     * @param userId
     * @return
     * @author Andy Chen
     */

    public String getUserNameById(int userId){
        //Log.d("database", "hii");
        //Log.d("database",String.valueOf(userId));
        //Log.d("database", "hii2");
        String ans = "cursor is null";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_TABLE WHERE ID = ?", new String[]{String.valueOf(userId)});
        if (cursor != null) {
            cursor.moveToFirst();
            ans = cursor.getString(1);
        }
        cursor.close();
        db.close();
        return ans;
    }

    /**
     * get user email by id
     * @param userId
     * @return
     * @author Sherry Gao
     */
    public String getUserEmail(int userId){
        String ans = null;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_TABLE WHERE ID = ?", new String[]{String.valueOf(userId)});
        if (cursor.moveToFirst()) {
            ans = cursor.getString(cursor.getColumnIndexOrThrow("EMAIL"));
        }
        cursor.close();
        db.close();
        return ans;
    }

    public boolean addTempEvent (Event event) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVT_NAME, event.getEvtName());
        cv.put(COLUMN_EVT_ID, event.getEvtId());
        cv.put(COLUMN_HOST_ID, event.getHostId());
        cv.put(COLUMN_EVT_TYPE, event.getEvtType());

        //-1 if failed to insert
        long insert = db.insert(EVENT_TABLE, null, cv);

        if (insert == -1) return false;
        return true;
    }

    /**
     * get name of an event with event
     * @param eventId
     * @return
     * @author Andy Chen
     */

    public String getEvtNamebyID(int eventId){
        String ans = "cursor is null";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_NAME = ?", new String[]{String.valueOf(eventId)});
        if (cursor != null) {
            cursor.moveToFirst();
            //ans = cursor.getString(1);
            ans = "New Event";
        }
        cursor.close();
        db.close();
        return ans;
    }

    /**
     * get all active && public events for the home page
     * @return
     */
    public List<Event> getAllActivePublicEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Event> allEvents = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                // check whether or not to add the evnt to the list
                boolean isPublic = true;
                if(!isPublic) {
                    cursor.moveToNext();
                    continue;
                }

                // Check whether the event is active or not
                String time = cursor.getString(cursor.getColumnIndexOrThrow("TIME"));
//                Calendar calendar = new GregorianCalendar();
//                calendar.getInstance();
//                int year = calendar.get(Calendar.YEAR);
//                int month = calendar.get(Calendar.MONTH) + 1;
//                int day = calendar.get(Calendar.DAY_OF_MONTH);
//                int hour = calendar.get(Calendar.HOUR);
//                int minute = calendar.get(Calendar.MINUTE);
//
//                String[] dueTime = time.split("-");
//                int[] dueTimeNum = new int[3];
//                for(int i = 0; i < 3; i++) {
//                    dueTimeNum[i] = Integer.parseInt(dueTime[i]);
//                }
//
//                if(dueTimeNum[0] < year || (dueTimeNum[0] == year && dueTimeNum[1] < month) || (dueTimeNum[0] == year && dueTimeNum[1] == month && dueTimeNum[2] < day)) {
//                    String[] dueHours = dueTime[3].split(":");
//                    int[] dueHoursNum = new int[2];
//                    for(int i = 0; i < 2; i++) {
//                        dueHoursNum[i] = Integer.parseInt(dueHours[i]);
//                    }
//
//                    if(dueHoursNum[0] < hour || (dueHoursNum[0] == hour && dueHoursNum[1] <= minute)) {
//                        cursor.moveToNext();
//                        continue;
//                    }
//                }

                int evtId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("EVT_ID")));
                String evtName = cursor.getString(cursor.getColumnIndexOrThrow("EVT_NAME"));
                int hostId = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("HOST_ID")));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("LOCATION"));
                int type = Integer.parseInt(cursor.getString(cursor.getColumnIndexOrThrow("TYPE")));

                Map<String, Integer> timeslots = new HashMap<>();
                Type classType = new TypeToken<Map<String, Integer>>() {}.getType();
                timeslots = new Gson().fromJson(cursor.getString(cursor.getColumnIndexOrThrow("TIMESLOTS")), classType);

                List<String> participants = new ArrayList<>();
                classType = new TypeToken<List<String>>() {}.getType();
                participants = new Gson().fromJson(cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS")), classType);

                // String duration = cursor.getString(cursor.getColumnIndexOrThrow("DURATION"));
                String duration = "12";
                String evtDescription = "Event Description";

                Event event = new Event(evtId, evtName, hostId, evtDescription, time, null, location, timeslots, participants, type, true, isPublic, duration);
                allEvents.add(event);
                cursor.moveToNext();
            }
        }

        return allEvents;
    }

    /**
     * Add user to event participants list
     * @param evtId
     * @param userEmail
     * @return boolean
     * @author Sherry Gao
     */
    public boolean signUpEvent(int evtId, String userEmail, Map<String, Integer> map) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[] {String.valueOf(evtId)});

        if(cursor.moveToFirst()) {
            String participantsString = cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS"));
            Type type = new TypeToken<List<String>>() {}.getType();
            List<String> participantsList = new Gson().fromJson(participantsString, type);

            participantsList.add(userEmail);
            participantsString = new Gson().toJson(participantsList);

            String timeslotsString = new Gson().toJson(map);

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PARTICIPANTS, participantsString);
            cv.put(COLUMN_TIMESLOTS, timeslotsString);
            Log.d("DATABASE", timeslotsString);
            db.update(EVENT_TABLE, cv, "EVT_ID" + "= ?", new String[] {String.valueOf(evtId)});

            // TODO: SEND NOTI TO HOST

            return true;
        }
        else
            return false;
    }

    //add notification to table

    /**
     * Add notification to table
     * @param noti
     * @return boolean
     * @author Andy
     */
    public boolean addNoti(Notification noti){
        SQLiteDatabase db = this .getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EVT_ID, noti.getEventID());
        cv.put(COLUMN_FROM_ID, noti.getFrom());
        cv.put(COLUMN_TO_ID, noti.getTo());
        cv.put(COLUMN_NOTIFICATION_TYPE, noti.getType());

        long insert = db.insert(NOTIFICATION_TABLE,null, cv);

        if (insert == -1) return false;
        return true;
    }

    /**
     * get notifications sent to specified user
     * @param user
     * @return list of notification
     * @author Andy
     */
    public List<Notification> getNotification(User user){

        List<Notification> notiList = new ArrayList<>();
        int ID = user.getUserId();

        //get data from notification table
        String queryString = "SELECT * FROM " + NOTIFICATION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString,null);

        if (cursor.moveToFirst()) {
            do {
                int tempID = cursor.getInt(3);
                if (tempID != ID) continue;
                Notification temp = new Notification(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                notiList.add(temp);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notiList;
    }


    //


}
