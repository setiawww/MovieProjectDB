package com.dicoding.setiawww.movieprojectdb.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by setiawww on 10/11/2017.
 */

public class DatabaseContract {

    public static String TABLE_FAVOURITE = "fave";

    public static final class FaveColumns implements BaseColumns {
        //Fave movie id
        public static String IDMOVIE = "idmovie";
        //Fave judul
        public static String JUDUL = "judul";
        //Fave detail
        public static String OVERVIEW = "overview";
        //Fave date
        public static String DATE = "date";
        //Fave poster
        public static String POSTER = "poster";
        //Fave status
        public static String STATUS = "status";
    }

    public static final String AUTHORITY = "com.dicoding.setiawww.movieprojectdb";

    public static final Uri CONTENT_URI = new Uri.Builder().scheme("content")
            .authority(AUTHORITY)
            .appendPath(TABLE_FAVOURITE)
            .build();

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString( cursor.getColumnIndex(columnName) );
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt( cursor.getColumnIndex(columnName) );
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong( cursor.getColumnIndex(columnName) );
    }
}
