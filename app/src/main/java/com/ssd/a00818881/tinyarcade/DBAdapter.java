package com.ssd.a00818881.tinyarcade;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter {
    //various constants to be used in creating and updating the database
    private static final String DATABASE_NAME = "MyDB";

    private static final String DATABASE_TABLE = "contacts";
    private static final String KEY_ROWID = "_id";
    private static final String KEY_FIRST_NAME = "firstName";
    private static final String KEY_LAST_NAME = "lastName";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_STREET_ADDRESS = "streetAddress";
    private static final String KEY_CITY = "city";
    private static final String KEY_PROVINCE_STATE = "provinceState";
    private static final String KEY_GAME_WINS = "gameWins";
    private static final String KEY_GAMES_PLAYED = "gamesPlayed";
    private static final String TAG = "DBAdapter";

    private static final String DATABASE_TABLE2 = "gameplays";
    private static final String KEY_DB2_ROWID = "_id";
    private static final String KEY_DB2_FIRST_NAME = "firstName";
    private static final String KEY_DB2_DATE = "date";
    private static final String KEY_DB2_GAMEPLAY = "gameplay";
    private static final String DB2_TAG = "GameplayDBAdapter";

    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_CREATE =
            "create table " + DATABASE_TABLE + " ("
                +KEY_ROWID+ " integer primary key autoincrement, "
                +KEY_FIRST_NAME+ " text not null, "
                +KEY_LAST_NAME+ " text not null, "
                +KEY_EMAIL+ " text not null, "
                +KEY_PHONE+ " text not null, "
                +KEY_STREET_ADDRESS+ " text not null, "
                +KEY_CITY+ " text not null, "
                +KEY_PROVINCE_STATE + " text not null, "
                +KEY_GAME_WINS + " int not null, "
                +KEY_GAMES_PLAYED +" int not null);";

    private static final String DATABASE2_CREATE =
            "create table " + DATABASE_TABLE2 + " ("
                    + KEY_DB2_ROWID + " integer primary key autoincrement, "
                    + KEY_DB2_FIRST_NAME + " integer not null, "
                    + KEY_DB2_DATE + " text not null, "
                    + KEY_DB2_GAMEPLAY + " text not null);";


    final Context context;


    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context ctx)
    {
        this.context = ctx;
        dbHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper
    {
        public DatabaseHelper(Context context)
        {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        //onCreate implicitly used to create the database table "contacts"
        @Override
        public void onCreate(SQLiteDatabase db)
        {
            try {
                db.execSQL(DATABASE_CREATE);

                db.execSQL(DATABASE2_CREATE);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        //onUpgrade called implicitly when the database "version" has changed
        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
        {
            Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE);

            Log.w(DB2_TAG, "Upgrading database from version " + oldVersion + " to "
                    + newVersion + ", which will destroy all old data");
            db.execSQL("DROP TABLE IF EXISTS "+DATABASE_TABLE2);
            onCreate(db);
        }}

    //---opens the database---
    //calls SQLiteOpenHelper.getWritableDatabase() when opening the DB.
    //If the DB does not yet exist it will be created here.
    public DBAdapter open() throws SQLException
    {
        db = dbHelper.getWritableDatabase();
        return this;
    }

    //---closes the database---
    public void close()
    {
        dbHelper.close();
    }

    //---insert a contact into the database---
    //uses ContentValues class to store key/value pairs for field names and data
    //to be inserted into the DB table by SQLiteDatabase.insert()
    public long insertContact(String firstName,String lastName,String email,
                              String phone,String streetAddress,String city,
                              String provinceState)
    {
        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_FIRST_NAME, firstName);
        initialValues.put(KEY_LAST_NAME,lastName);
        initialValues.put(KEY_EMAIL, email);
        initialValues.put(KEY_PHONE,phone);
        initialValues.put(KEY_STREET_ADDRESS,streetAddress);
        initialValues.put(KEY_CITY,city);
        initialValues.put(KEY_PROVINCE_STATE,provinceState);
        initialValues.put(KEY_GAME_WINS,0);
        initialValues.put(KEY_GAMES_PLAYED,0);
        return db.insert(DATABASE_TABLE, null, initialValues);
    }

    //---deletes a particular contact---
    //removes from the DB the record specified using SQLiteDatabase.delete()
    public boolean deleteContact(long rowId)
    {
        return db.delete(DATABASE_TABLE, KEY_ROWID + "=" + rowId, null) > 0;
    }

    //---retrieves all the contacts---
    //SQLiteDatabase.query builds a SELECT query and returns a "Cursor" over the result set
    public Cursor getAllContacts()
    {
        return db.query(DATABASE_TABLE, new String[] {KEY_ROWID, KEY_FIRST_NAME,KEY_LAST_NAME,
                KEY_EMAIL,KEY_PHONE,KEY_STREET_ADDRESS,KEY_CITY,KEY_PROVINCE_STATE,
                KEY_GAME_WINS,KEY_GAMES_PLAYED}, null, null, null, null, null);
    }

    //---retrieves a particular contact---
    public Cursor getContact(long rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE, new String[] {KEY_ROWID,
                                KEY_FIRST_NAME,KEY_LAST_NAME,
                                KEY_EMAIL,KEY_PHONE,KEY_STREET_ADDRESS,
                                KEY_CITY,KEY_PROVINCE_STATE,
                                KEY_GAME_WINS,KEY_GAMES_PLAYED}, KEY_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    //---updates a contact---
    //Uses SQLiteDatabase.update() to change existing data by key/value pairs
    public boolean updateContact(long rowId, String firstName, String lastName,
                                 String email,String phone,String streetAddress,
                                 String city,String provinceState)
    {
        ContentValues args = new ContentValues();
        args.put(KEY_FIRST_NAME, firstName);
        args.put(KEY_LAST_NAME,lastName);
        args.put(KEY_EMAIL, email);
        args.put(KEY_PHONE,phone);
        args.put(KEY_STREET_ADDRESS,streetAddress);
        args.put(KEY_CITY,city);
        args.put(KEY_PROVINCE_STATE,provinceState);
        return db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + rowId, null) > 0;
    }

    public Cursor getAllGameplays()
    {
        return db.query(DATABASE_TABLE2, new String[] {KEY_DB2_ROWID, KEY_DB2_FIRST_NAME,
                KEY_DB2_DATE, KEY_DB2_GAMEPLAY}, null, null, null, null, null);
    }

    public long insertGameplay(int contactId,String date,String gameplay, boolean winner)
    {
        ContentValues args = new ContentValues();
        Cursor cursor = getContact(contactId);
        if(winner){
            args.put(KEY_GAME_WINS, cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(8)))+1);
        }
        args.put(KEY_GAMES_PLAYED, cursor.getInt(cursor.getColumnIndex(cursor.getColumnName(9)))+1);
        db.update(DATABASE_TABLE, args, KEY_ROWID + "=" + contactId, null);

        ContentValues initialValues = new ContentValues();
        initialValues.put(KEY_DB2_FIRST_NAME, contactId);
        initialValues.put(KEY_DB2_DATE,date);
        initialValues.put(KEY_DB2_GAMEPLAY, gameplay);

        return db.insert(DATABASE_TABLE2, null, initialValues);
    }

    public Cursor getGameplayByContact(int rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_DB2_ROWID,
                                KEY_DB2_FIRST_NAME,
                                KEY_DB2_DATE, KEY_DB2_GAMEPLAY}, KEY_DB2_FIRST_NAME + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }

    public Cursor getGameplay(int rowId) throws SQLException
    {
        Cursor mCursor =
                db.query(true, DATABASE_TABLE2, new String[] {KEY_DB2_ROWID,
                                KEY_DB2_FIRST_NAME,
                                KEY_DB2_DATE, KEY_DB2_GAMEPLAY}, KEY_DB2_ROWID + "=" + rowId, null,
                        null, null, null, null);
        if (mCursor != null) {
            mCursor.moveToFirst();
        }
        return mCursor;
    }
}
