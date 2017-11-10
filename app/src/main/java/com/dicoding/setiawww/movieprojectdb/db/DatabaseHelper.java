package com.dicoding.setiawww.movieprojectdb.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by setiawww on 10/11/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "dbfavemovie";
    private static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_TABLE_FAVE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVOURITE,
            DatabaseContract.FaveColumns._ID,
            DatabaseContract.FaveColumns.IDMOVIE,
            DatabaseContract.FaveColumns.JUDUL,
            DatabaseContract.FaveColumns.OVERVIEW,
            DatabaseContract.FaveColumns.DATE,
            DatabaseContract.FaveColumns.POSTER,
            DatabaseContract.FaveColumns.STATUS
    );

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVOURITE);
        onCreate(db);
    }

}
