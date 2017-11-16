package com.dicoding.setiawww.movieprojectdb.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.dicoding.setiawww.movieprojectdb.db.DatabaseContract;
import com.dicoding.setiawww.movieprojectdb.db.FaveHelper;

import java.sql.SQLException;

import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.AUTHORITY;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.CONTENT_URI;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.IDMOVIE;

/**
 * Created by setiawww on 16/11/2017.
 */

public class FaveProvider extends ContentProvider {

    private static final int FAVE = 1;
    private static final int FAVE_ID = 2;
    private static final int FAVE_IDMOVIE = 3;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {

        // content://com.dicoding.setiawww.movieprojectdb/fave
        sUriMatcher.addURI(AUTHORITY, DatabaseContract.TABLE_FAVOURITE, FAVE);

        // content://com.dicoding.setiawww.movieprojectdb/fave/id
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAVOURITE+ "/#",
                FAVE_ID);

        // content://com.dicoding.setiawww.movieprojectdb/fave/idmovie
        //sUriMatcher.addURI(AUTHORITY,
        //        DatabaseContract.TABLE_FAVOURITE+ "/#",
        //        FAVE_IDMOVIE);
        sUriMatcher.addURI(AUTHORITY,
                DatabaseContract.TABLE_FAVOURITE+ "/" +IDMOVIE + "/#",
                FAVE_IDMOVIE);
    }

    private FaveHelper faveHelper;

    @Override
    public boolean onCreate() {
        faveHelper = new FaveHelper(getContext());
        try {
            faveHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        Cursor cursor;
        switch(sUriMatcher.match(uri)){
            case FAVE:
                cursor = faveHelper.queryProvider();
                break;
            case FAVE_ID:
                cursor = faveHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case FAVE_IDMOVIE:
                cursor = faveHelper.queryByIdMovieProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        if (cursor != null){
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {

        long added ;

        switch (sUriMatcher.match(uri)){
            case FAVE:
                added = faveHelper.insertProvider(contentValues);
                break;
            default:
                added = 0;
                break;
        }

        if (added > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return Uri.parse(CONTENT_URI + "/" + added);
    }


    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        int updated ;
        switch (sUriMatcher.match(uri)) {
            case FAVE_ID:
                updated =  faveHelper.updateProvider(uri.getLastPathSegment(),contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        if (updated > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case FAVE_ID:
                deleted =  faveHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case FAVE_IDMOVIE:
                deleted =  faveHelper.deleteProvider2(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        if (deleted > 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return deleted;
    }

}
