package com.dicoding.setiawww.movieprojectdb.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.dicoding.setiawww.movieprojectdb.entity.Favourite;

import java.sql.SQLException;
import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.DATE;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.IDMOVIE;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.OVERVIEW;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.JUDUL;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.POSTER;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.STATUS;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.TABLE_FAVOURITE;

/**
 * Created by setiawww on 10/11/2017.
 */

public class FaveHelper {

    private static String DATABASE_TABLE = TABLE_FAVOURITE;
    private Context context;
    private DatabaseHelper dataBaseHelper;

    private SQLiteDatabase database;

    public FaveHelper(Context context){
        this.context = context;
    }

    public FaveHelper open() throws SQLException {
        dataBaseHelper = new DatabaseHelper(context);
        database = dataBaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        dataBaseHelper.close();
    }

    public ArrayList<Favourite> query(){
        ArrayList<Favourite> arrayList = new ArrayList<Favourite>();
        Cursor cursor = database.query(DATABASE_TABLE,null,null,null,null,null,_ID +" DESC",null);
        cursor.moveToFirst();
        Favourite favourite;
        if (cursor.getCount()>0) {
            do {

                favourite = new Favourite();
                favourite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favourite.setIdmovie(cursor.getString(cursor.getColumnIndexOrThrow(IDMOVIE)));
                favourite.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                favourite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favourite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                favourite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favourite.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)));

                arrayList.add(favourite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public ArrayList<Favourite> getDataByMovieID(String movieID){
        String result = "";
        Cursor cursor = database.query(DATABASE_TABLE,null,IDMOVIE+" LIKE ?",new String[]{movieID},null,null,_ID + " ASC",null);
        cursor.moveToFirst();
        ArrayList<Favourite> arrayList = new ArrayList<>();
        Favourite favourite;
        if (cursor.getCount()>0) {
            do {
                favourite = new Favourite();
                favourite.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                favourite.setIdmovie(cursor.getString(cursor.getColumnIndexOrThrow(IDMOVIE)));
                favourite.setJudul(cursor.getString(cursor.getColumnIndexOrThrow(JUDUL)));
                favourite.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                favourite.setDate(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                favourite.setPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                favourite.setStatus(cursor.getString(cursor.getColumnIndexOrThrow(STATUS)));

                arrayList.add(favourite);
                cursor.moveToNext();

            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insert(Favourite favourite){
        ContentValues initialValues =  new ContentValues();
        initialValues.put(IDMOVIE, favourite.getIdmovie());
        initialValues.put(JUDUL, favourite.getJudul());
        initialValues.put(OVERVIEW, favourite.getOverview());
        initialValues.put(DATE, favourite.getDate());
        initialValues.put(POSTER, favourite.getPoster());
        initialValues.put(STATUS, favourite.getStatus());
        return database.insert(DATABASE_TABLE, null, initialValues);
    }

    public int update(Favourite favourite){
        ContentValues args = new ContentValues();
        args.put(IDMOVIE, favourite.getIdmovie());
        args.put(JUDUL, favourite.getJudul());
        args.put(OVERVIEW, favourite.getOverview());
        args.put(DATE, favourite.getDate());
        args.put(POSTER, favourite.getPoster());
        args.put(STATUS, favourite.getStatus());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + favourite.getId() + "'", null);
    }

    public int delete(int id){
        return database.delete(TABLE_FAVOURITE, _ID + " = '"+id+"'", null);
    }

    public Cursor queryByIdProvider(String id){
        return database.query(DATABASE_TABLE
                ,null
                ,_ID + " = ?"
                ,new String[]{id}
                ,null
                ,null
                ,null
                ,null);
    }

    public Cursor queryProvider(){
        return database.query(DATABASE_TABLE
                ,null
                ,null
                ,null
                ,null
                ,null
                ,_ID + " ASC");
    }

    public Cursor queryByIdMovieProvider(String idmovie){
        return database.query(DATABASE_TABLE
                ,null
                ,IDMOVIE + " = ?"
                ,new String[]{idmovie}
                ,null
                ,null
                ,null
                ,null);
    }

    public long insertProvider(ContentValues values){
        return database.insert(DATABASE_TABLE,null,values);
    }

    public int updateProvider(String id,ContentValues values){
        return database.update(DATABASE_TABLE,values,_ID +" = ?",new String[]{id} );
    }

    public int deleteProvider(String id){
        return database.delete(DATABASE_TABLE,_ID + " = ?", new String[]{id});
    }

    public int deleteProvider2(String idmovie){
        return database.delete(DATABASE_TABLE,IDMOVIE + " = ?", new String[]{idmovie});
    }
}
