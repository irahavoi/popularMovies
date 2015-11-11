package com.example.irahavoi.popularmovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import static com.example.irahavoi.popularmovies.data.MovieContract.MovieEntry.*;

public class MovieDbHelper extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 4;
    private static final String DATABASE_NAME = "movies.db";
    public static final String TABLE_MOVIES = "movies";

    //Dabase creation statement:
    private static final String DATABASE_CREATE = "create table " +
            TABLE_MOVIES + "(" +
            BaseColumns._ID + " integer primary key, " +
            ORIGINAL_LANGUAGE + " text, " +
            ORIGINAL_TITLE + " text, " +
            OVERVIEW + " text, " +
            RELEASE_DATE + " text, " +
            POSTER_PATH + " text," +
            POPULARITY + " numeric, " +
            TITLE + " text, " +
            VIDEO + " text, " +
            VOTE_AVERAGE + " numeric, " +
            VOTE_COUNT + " numeric);";

    public MovieDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    /**
     * Database creation logic.
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
    }

    /**
     * Database upgrade logic.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MovieDbHelper.class.getName(), "Upgrading movie db from version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
        onCreate(db);
    }
}
