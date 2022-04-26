package com.example.easyteamup;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.util.Pair;

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
    public static final String COLUMN_NOTICNT = "NOTI";
    public static final String COLUMN_INVCNT = "INV";

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
    public static final String COLUMN_EVT_DETERMINED_TIME = "DETERMINED_TIME";


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
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PWD + " TEXT, " + COLUMN_STUDENT + " INT, " + COLUMN_NOTICNT + " INT, " + COLUMN_INVCNT + " INT)";
        String createEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_NAME + " TEXT, " + COLUMN_HOST_ID + " INT, " + COLUMN_TIME + " TEXT, "  + COLUMN_LOCATION + " TEXT, " + COLUMN_TIMESLOTS + " TEXT, " + COLUMN_PARTICIPANTS + " TEXT, " + COLUMN_EVT_DURATION + " TEXT, " + COLUMN_EVT_TYPE + " INT, " + COLUMN_EVT_DESCRIPTION + " TEXT, " + COLUMN_EVT_PUBLIC + " BOOLEAN, " + COLUMN_EVT_DETERMINED_TIME + " TEXT )";
        String createNotiTableStatement = "CREATE TABLE " + NOTIFICATION_TABLE + " (" + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_ID + " INT, " + COLUMN_FROM_ID + " INT, " + COLUMN_TO_ID + " INT, " + COLUMN_NOTIFICATION_TYPE + " INT)";
        db.execSQL(createEventTableStatement);
        db.execSQL(createUserTableStatement);
        db.execSQL(createNotiTableStatement);


//        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
//        String dropEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_NAME + " TEXT, " + COLUMN_HOST_ID + " INT, " + COLUMN_TIME + " TEXT, "  + COLUMN_LOCATION + " TEXT, " + COLUMN_TIMESLOTS + " TEXT, " + COLUMN_PARTICIPANTS + " TEXT, " + COLUMN_EVT_DURATION + " TEXT, " + COLUMN_EVT_TYPE + " INT, " + COLUMN_EVT_DESCRIPTION + " TEXT, " + COLUMN_EVT_PUBLIC + " BOOLEAN, " + COLUMN_EVT_DETERMINED_TIME + " TEXT )";
//        db.execSQL(dropEventTableStatement);
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
     * @author Sherry Gao / Andy
     */
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + NOTIFICATION_TABLE);
        String createUserTableStatement = "CREATE TABLE " + USER_TABLE + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_AGE + " INT, " + COLUMN_EMAIL + " TEXT, " + COLUMN_PWD + " TEXT, " + COLUMN_STUDENT + " INT, " + COLUMN_NOTICNT + " INT, " + COLUMN_INVCNT + " INT)";
        String createEventTableStatement = "CREATE TABLE " + EVENT_TABLE + " (" + COLUMN_EVT_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_NAME + " TEXT, " + COLUMN_HOST_ID + " INT, " + COLUMN_TIME + " TEXT, "  + COLUMN_LOCATION + " TEXT, " + COLUMN_TIMESLOTS + " TEXT, " + COLUMN_PARTICIPANTS + " TEXT, " + COLUMN_EVT_DURATION + " TEXT, " + COLUMN_EVT_TYPE + " INT, " + COLUMN_EVT_DESCRIPTION + " TEXT, " + COLUMN_EVT_PUBLIC + " BOOLEAN, " + COLUMN_EVT_DETERMINED_TIME + " TEXT )";
        String createNotiTableStatement = "CREATE TABLE " + NOTIFICATION_TABLE + " (" + COLUMN_NOTIFICATION_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_EVT_ID + " INT, " + COLUMN_FROM_ID + " INT, " + COLUMN_TO_ID + " INT, " + COLUMN_NOTIFICATION_TYPE + " INT)";
        db.execSQL(createEventTableStatement);
        db.execSQL(createUserTableStatement);
        db.execSQL(createNotiTableStatement);

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
    public int addEvent (Event event, List<Integer> invitees, boolean isEdit){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if(isEdit) {
            Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[]{String.valueOf(event.getEvtId())});
            if (cursor.moveToFirst()) {
                String timeSlotsString = new Gson().toJson(event.getEvtTimeSlots());
                cv.put(COLUMN_EVT_NAME, event.getEvtName());
                cv.put(COLUMN_TIME, event.getEvtSignUpDueDate());
                cv.put(COLUMN_LOCATION, event.getEvtLocation());
                cv.put(COLUMN_EVT_TYPE, String.valueOf(event.getEvtType()));
                cv.put(COLUMN_TIMESLOTS, timeSlotsString);
                cv.put(COLUMN_EVT_DURATION, event.getEvtDuration());
                cv.put(COLUMN_EVT_PUBLIC, event.isPublic());
                cv.put(COLUMN_EVT_DESCRIPTION, event.getEvtDescription());
                db.update(EVENT_TABLE, cv, "EVT_ID" + "= ?", new String[] {String.valueOf(event.getEvtId())});

                // send out invites
                for(Integer invitee : invitees) {
                    Notification invite = new Notification(event.getEvtId(), event.getHostId(), invitee, 3);
                    addNoti(invite);
                }

                Type classType = new TypeToken<List<Integer>>() {}.getType();
                String buf = cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS"));
                List<Integer> participants = new Gson().fromJson(buf, classType);

                // TODO: do i use type 2?
                for(Integer p : participants) {
                    Notification invite = new Notification(event.getEvtId(), event.getHostId(), p, 2);
                    addNoti(invite);
                }
                return event.getEvtId();
            }

            return -1;
        }
        else {
            String participantsString = new Gson().toJson(event.getEvtParticipants());
            String timeSlotsString = new Gson().toJson(event.getEvtTimeSlots());

            cv.put(COLUMN_EVT_NAME, event.getEvtName());
            cv.put(COLUMN_HOST_ID, String.valueOf(event.getHostId()));
            cv.put(COLUMN_TIME, event.getEvtSignUpDueDate());
            cv.put(COLUMN_LOCATION, event.getEvtLocation());
            cv.put(COLUMN_EVT_TYPE, String.valueOf(event.getEvtType()));
            cv.put(COLUMN_TIMESLOTS, timeSlotsString);
            cv.put(COLUMN_PARTICIPANTS, participantsString);
            cv.put(COLUMN_EVT_DURATION, event.getEvtDuration());
            cv.put(COLUMN_EVT_PUBLIC, event.isPublic());
            cv.put(COLUMN_EVT_DESCRIPTION, event.getEvtDescription());
            cv.put(COLUMN_EVT_DETERMINED_TIME, event.getEvtDeterminedTime());

            //-1 if failed to insert
            long insert = db.insert(EVENT_TABLE,null, cv);

            if (insert == -1) return -1;

            // send out invites
            for(Integer invitee : invitees) {
                Log.d("DEBUG", String.valueOf(invitee));
                Notification invite = new Notification(Math.toIntExact(insert), event.getHostId(), invitee, 2);
                addNoti(invite);
            }
            return Math.toIntExact(insert);
        }
    }

    /**
     * get userId by email
     * @param email
     * @return
     * @author Sherry Gao
     */
    public int getUserIdByEmail(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM USER_TABLE WHERE EMAIL = ?", new String[]{String.valueOf(email)});

        if(cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
        }
        else {
            return -1;
        }
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
            //TODO: TESTING change user name needs to be implemented
            //CORRECT VERSION:
            ans = cursor.getString(1);
            //ans = "Zhaoxu";
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
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[]{String.valueOf(eventId)});
        if (cursor.moveToFirst()) {
            //TODO: TESTING
            ans = cursor.getString(cursor.getColumnIndexOrThrow("EVT_NAME"));
            //ans = "New Event";
        }
        cursor.close();
        db.close();
        return ans;
    }

    /**
     * get all active && public events for the home page
     * @return
     * @author Sherry Gao
     */
    public List<Event> getAllActivePublicEvents() {
        SQLiteDatabase db = this.getWritableDatabase();
        List<Event> allEvents = new ArrayList<>();

        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE",null);

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {

                // check whether or not to add the evnt to the list
                int isPublic = cursor.getInt(cursor.getColumnIndexOrThrow("IS_PUBLIC"));
                if(isPublic == 0) {
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

                List<Integer> participants = new ArrayList<>();
                classType = new TypeToken<List<Integer>>() {}.getType();
                participants = new Gson().fromJson(cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS")), classType);

                String duration = cursor.getString(cursor.getColumnIndexOrThrow("DURATION"));
                String evtDescription = cursor.getString(cursor.getColumnIndexOrThrow("EVT_DESCRIPTION"));

                Event event = new Event(evtId, evtName, hostId, evtDescription, time, null, location, timeslots, participants, type, true, true, duration);
                allEvents.add(event);
                cursor.moveToNext();
            }
        }

        return allEvents;
    }

    /**
     * Add user to event participants list
     * @param evtId
     * @param userId
     * @return boolean
     * @author Sherry Gao
     */
    public boolean signUpEvent(int evtId, int userId, Map<String, Integer> map) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[] {String.valueOf(evtId)});

        if(cursor.moveToFirst()) {
            String participantsString = cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS"));
            Type type = new TypeToken<List<Integer>>() {}.getType();
            List<Integer> participantsList = new Gson().fromJson(participantsString, type);

            participantsList.add(userId);
            participantsString = new Gson().toJson(participantsList);

            String timeslotsString = new Gson().toJson(map);

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_PARTICIPANTS, participantsString);
            cv.put(COLUMN_TIMESLOTS, timeslotsString);
            db.update(EVENT_TABLE, cv, "EVT_ID" + "= ?", new String[] {String.valueOf(evtId)});

            // send notification to event host
            int evtHostId = cursor.getInt(cursor.getColumnIndexOrThrow("HOST_ID"));
            Notification invite = new Notification(evtId, userId, evtHostId, 1);
            addNoti(invite);

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
                int type = cursor.getInt(4);
                if (tempID != ID) continue;
                if (type == 2) continue;
                Notification temp = new Notification(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                notiList.add(temp);
            } while(cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return notiList;
    }

    /**
     * get notifications sent to specified user
     * @param user
     * @return list of notification
     * @author Andy
     */
    public List<Notification> getInvitations(User user) {

        List<Notification> inviList = new ArrayList<>();
        int ID = user.getUserId();

        //get data from notification table
        String queryString = "SELECT * FROM " + NOTIFICATION_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(queryString, null);

        if (cursor.moveToFirst()) {
            do {
                int tempID = cursor.getInt(3);
                int tempNotiId = cursor.getInt(4);
                if (tempID != ID) continue;
                if (tempNotiId != 2) continue;
                Notification temp = new Notification(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4));
                inviList.add(temp);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return inviList;
    }
    /**
     * determine best time slot for event
     * @param evtId
     * @return
     * @author Sherry Gao
     */
    public String determineTimeSlots(int evtId) {
        SQLiteDatabase db = this .getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[] {String.valueOf(evtId)});

        if(cursor.moveToFirst()) {
            String timeslotsString = cursor.getString(cursor.getColumnIndexOrThrow("TIMESLOTS"));
            Type classType = new TypeToken<Map<String, Integer>>() {}.getType();
            Map<String, Integer> timeslots = new Gson().fromJson(timeslotsString, classType);

            Map.Entry<String, Integer> maxEntry = null;

            for (Map.Entry<String, Integer> entry : timeslots.entrySet())
            {
                if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0)
                {
                    maxEntry = entry;
                }
            }

            String maxTimeSlot = maxEntry.getKey();

            ContentValues cv = new ContentValues();
            cv.put(COLUMN_EVT_DETERMINED_TIME, maxTimeSlot);
            db.update(EVENT_TABLE, cv, "EVT_ID" + "= ?", new String[] {String.valueOf(evtId)});

            String participantsString = cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS"));
            int evtHostId = cursor.getInt(cursor.getColumnIndexOrThrow("HOST_ID"));

            classType = new TypeToken<List<Integer>>() {}.getType();
            List<Integer> participants = new Gson().fromJson(participantsString, classType);

            for(Integer i : participants) {
                Notification invite = new Notification(evtId, evtHostId, i, 2);
                addNoti(invite);
            }

            return maxTimeSlot;
        }

        return "";
    }

    /**
     * get determined time slot
     * @param evtId
     * @return
     * @author Sherry Gao
     */
    public String getDeterminedTimeSlot(int evtId) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[] {String.valueOf(evtId)});

        String determinedTime = "";

        if(cursor.moveToFirst()) {
            determinedTime = cursor.getString(cursor.getColumnIndexOrThrow("DETERMINED_TIME"));
        }

        return determinedTime;
    }

    /**
     * Withdraw from event
     * @param evtId
     * @param userId
     * @return boolean
     * @author Sherry Gao
     */
    public boolean withdrawEvent(int evtId, int userId) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM EVENT_TABLE WHERE EVT_ID = ?", new String[]{String.valueOf(evtId)});

        if (cursor.moveToFirst()) {
            String participants = cursor.getString(cursor.getColumnIndexOrThrow("PARTICIPANTS"));
            Type classType = new TypeToken<List<Integer>>() {
            }.getType();
            List<Integer> participantsList = new Gson().fromJson(participants, classType);

            if (participantsList.contains(userId)) {
                participantsList.remove(Integer.valueOf(userId));
                String newParticipantsList = new Gson().toJson(participantsList);
                ContentValues cv = new ContentValues();

                cv.put(COLUMN_PARTICIPANTS, newParticipantsList);
                db.update(EVENT_TABLE, cv, "EVT_ID" + "= ?", new String[] {String.valueOf(evtId)});

                int evtHostId = cursor.getInt(cursor.getColumnIndexOrThrow("HOST_ID"));
                Notification invite = new Notification(evtId, userId, evtHostId, 0);
                addNoti(invite);
            }
            return true;
        }
        else
            return false;
    }
    /**
     * Delete notification
     * @param fromId
     * @param toId
     * @param type
     * @author Andy
     */
    public void deleteNoti(int fromId, int toId, int type){
        Log.d("dbdelete " +fromId+toId+type, "deleting noti from db");
        SQLiteDatabase db = this.getWritableDatabase();
        //db.execSQL("DELETE FROM "+NOTIFICATION_TABLE+" WHERE NOTI_ID = "+String.valueOf(notiId));
        db.execSQL("DELETE FROM "+NOTIFICATION_TABLE+" WHERE FROM_ID = "+String.valueOf(fromId) + " AND TO_ID = " + String.valueOf(toId) + " AND TYPE = "+String.valueOf(type));
    }

    /**
     *
     * @param receiverID
     * @return
     * @author Andy
     */
    public Pair<Integer, Integer> getNewNoti(int receiverID){
        Log.d("debug","checking new notificaiotn since last login for "+receiverID);
        int inv = 0, noti = 0;
        int oldInv = 0, oldNoti = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM " + NOTIFICATION_TABLE;
        Cursor cursor = db.rawQuery(queryString, null);
        if (cursor.moveToFirst()) {
            do {
                int tempID = cursor.getInt(3);
                int tempNotiId = cursor.getInt(4);
                if (tempID != receiverID) continue;
                if (tempNotiId == 2) inv++;
                else noti++;
            } while (cursor.moveToNext());
        }
        Cursor cursor2 = db.rawQuery("SELECT * FROM "+USER_TABLE+" WHERE ID = ?",new String[]{String.valueOf(receiverID)});
        if (cursor2.moveToFirst()) {
            oldNoti = cursor2.getInt(cursor2.getColumnIndexOrThrow(COLUMN_NOTICNT));
            oldInv = cursor2.getInt(cursor2.getColumnIndexOrThrow(COLUMN_INVCNT));
        }
        cursor2.close();
        cursor.close();
        db.close();
        return Pair.create(noti-oldNoti,inv-oldInv);
    }
    //get notification
    //public int getNotiId()

    /**
     *
     * @param userID
     * @param notiCnt
     * @param invCnt
     * @return
     * @author Andy
     */
    public boolean updateNotificationCount(int userID, int notiCnt, int invCnt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_NOTICNT,notiCnt);
        cv.put(COLUMN_INVCNT, invCnt);
        return db.update(USER_TABLE, cv, "ID = ?", new String[]{String.valueOf(userID)}) == 1;
    }
}
