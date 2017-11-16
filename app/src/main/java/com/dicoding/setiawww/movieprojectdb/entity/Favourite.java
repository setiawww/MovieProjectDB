package com.dicoding.setiawww.movieprojectdb.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.dicoding.setiawww.movieprojectdb.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.getColumnInt;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.getColumnString;

/**
 * Created by setiawww on 10/11/2017.
 */

public class Favourite implements Parcelable {

    private int id;
    private String idmovie;
    private String judul;
    private String overview;
    private String date;
    private String poster;
    private String status;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdmovie() {
        return idmovie;
    }

    public void setIdmovie(String idmovie) {
        this.idmovie = idmovie;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.idmovie);
        dest.writeString(this.judul);
        dest.writeString(this.overview);
        dest.writeString(this.date);
        dest.writeString(this.poster);
        dest.writeString(this.status);
    }

    public Favourite() {
    }

    public Favourite(Cursor cursor){
        this.id = getColumnInt(cursor, _ID);
        this.idmovie = getColumnString(cursor, DatabaseContract.FaveColumns.IDMOVIE);
        this.judul = getColumnString(cursor, DatabaseContract.FaveColumns.JUDUL);
        this.overview = getColumnString(cursor, DatabaseContract.FaveColumns.OVERVIEW);
        this.date = getColumnString(cursor, DatabaseContract.FaveColumns.DATE);
        this.poster = getColumnString(cursor, DatabaseContract.FaveColumns.POSTER);
        this.status = getColumnString(cursor, DatabaseContract.FaveColumns.STATUS);
    }

    protected Favourite(Parcel in) {
        this.id = in.readInt();
        this.idmovie = in.readString();
        this.judul = in.readString();
        this.overview = in.readString();
        this.date = in.readString();
        this.poster = in.readString();
        this.status = in.readString();
    }

    public static final Parcelable.Creator<Favourite> CREATOR = new Parcelable.Creator<Favourite>() {
        @Override
        public Favourite createFromParcel(Parcel source) {
            return new Favourite(source);
        }

        @Override
        public Favourite[] newArray(int size) {
            return new Favourite[size];
        }
    };
}
