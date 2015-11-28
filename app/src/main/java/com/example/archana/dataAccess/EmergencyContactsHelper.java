package com.example.archana.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Archana on 11/19/2015.
 */
public class EmergencyContactsHelper extends SQLiteOpenHelper{

    public static final String TABLE_EMERGENCYCONTACTS = "emergencyContacts";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_EM_CONTACT = "blocked";

    private static final String DATABASE_NAME = "Carma.db";
    private static final int DATABASE_VERSION = 1;

    public EmergencyContactsHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_CREATE = "create table "
            + TABLE_EMERGENCYCONTACTS + "(" + COLUMN_ID
            + " integer primary key autoincrement, " + COLUMN_EM_CONTACT
            + " text not null);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BlockedAppsHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EMERGENCYCONTACTS);
        onCreate(db);
    }
}
