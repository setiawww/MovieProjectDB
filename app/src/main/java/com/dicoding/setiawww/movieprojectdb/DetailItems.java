package com.dicoding.setiawww.movieprojectdb;

import org.json.JSONObject;

/**
 * Created by setiawww on 9/27/2017.
 */

public class DetailItems {
    private int id;
    private String judul;
    private String detail;
    private String date;
    private String poster;
    private String status;

    public DetailItems(JSONObject object){

        try {
            int id = object.getInt("id");
            String judul = object.getString("title");
            String detail = object.getString("overview");
            String date = object.getString("release_date");
            String poster = object.getString("poster_path");
            String status = object.getString("status");

            this.id = id;
            this.judul = judul;
            this.detail = detail;
            this.date = date;
            this.poster = poster;
            this.status = status;

        }catch (Exception e){

            e.printStackTrace();
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }
    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getDetail() {
        return detail;
    }
    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String smallPoster) {
        this.poster = poster;
    }

    public String getDate(){
        return date;
    }

    public void setDate(String date){
        this.date = date;
    }

    public String getStatus(){
        return status;
    }

    public void setStatus(String status){
        this.status = status;
    }

}
