package com.dicoding.setiawww.movieprojectdb;

import org.json.JSONObject;

/**
 * Created by setiawww on 6/10/2017.
 */

public class Movie {
    private String id, judul, overview, tanggal, poster;

    public Movie(JSONObject object){

        try {
            String id = object.getString("id");
            String judul = object.getString("title");
            String overview = object.getString("overview");
            String date = object.getString("release_date");
            String poster = object.getString("poster_path");

            this.id = id;
            this.judul = judul;
            this.overview = overview;
            this.tanggal = date;
            this.poster = poster;

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }
}
