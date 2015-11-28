package com.example.archana.dataAccess;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Archana on 11/19/2015.
 */
public class BlockedAppsHelper extends SQLiteOpenHelper{

    public static final String TABLE_BLOCKEDAPPS = "blockedAppsNew";
    public static final String PRIMARY_KEY = "_id";
    public static final String APP_NAME = "appName";
    public static final String IS_BLOCKED = "isBlocked";

    private static final String DATABASE_NAME = "Carma.db";
    private static final int DATABASE_VERSION = 1;

    public BlockedAppsHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    private static final String DATABASE_CREATE = "create table "
        + TABLE_BLOCKEDAPPS + "(" + PRIMARY_KEY
        + " integer primary key autoincrement, " + APP_NAME
        + " text not null, "+ IS_BLOCKED+ " text not null);";


    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("CREATING TABLE",DATABASE_CREATE);
        db.execSQL(DATABASE_CREATE);
    }

    public void delete(SQLiteDatabase db){
        db.execSQL("delete from "+TABLE_BLOCKEDAPPS);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(BlockedAppsHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_BLOCKEDAPPS);
        onCreate(db);
    }

    public void update(SQLiteDatabase db){


        db.execSQL("update " + TABLE_BLOCKEDAPPS+ " set ");
    }

}
