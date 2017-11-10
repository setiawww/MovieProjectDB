package com.dicoding.setiawww.movieprojectdb.db;

import android.provider.BaseColumns;

/**
 * Created by setiawww on 10/11/2017.
 */

public class DatabaseContract {

    static String TABLE_FAVOURITE = "fave";

    static final class FaveColumns implements BaseColumns {
        //Fave movie id
        static String IDMOVIE = "idmovie";
        //Fave judul
        static String JUDUL = "judul";
        //Fave detail
        static String OVERVIEW = "overview";
        //Fave date
        static String DATE = "date";
        //Fave poster
        static String POSTER = "poster";
        //Fave status
        static String STATUS = "status";
    }
}
