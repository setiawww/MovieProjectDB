package com.dicoding.setiawww.movieprojectdb.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.setiawww.movieprojectdb.Movie;
import com.dicoding.setiawww.movieprojectdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.bumptech.glide.Glide;

/**
 * Created by setiawww on 6/10/2017.
 */

public class ListMovieAdapter extends RecyclerView.Adapter<ListMovieAdapter.CategoryViewHolder>{
    private Context context;

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }
    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    private ArrayList<Movie>listMovie;

    public ListMovieAdapter(Context context) {
        this.context = context;
    }

    @Override
    public CategoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemRow = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row_movie, parent, false);
        return new CategoryViewHolder(itemRow);
    }

    @Override
    public void onBindViewHolder(CategoryViewHolder holder, int position) {

        holder.tvJudul.setText(getListMovie().get(position).getJudul());
        holder.tvOverview.setText(getListMovie().get(position).getOverview());
        holder.tvTanggal.setText(getListMovie().get(position).getTanggal());

        //Glide.with(context)
        //        .load("http://image.tmdb.org/t/p/w185"+getListMovie().get(position).getPoster())
        //        .override(100, 150)
        //        .crossFade()
        //        .into(holder.imgPoster);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w185"+(getListMovie().get(position).getPoster())).into(holder.imgPoster);
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder{
        TextView tvJudul;
        TextView tvOverview;
        TextView tvTanggal;
        ImageView imgPoster;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_judul);
            tvOverview = (TextView)itemView.findViewById(R.id.tv_overview);
            tvTanggal = (TextView)itemView.findViewById(R.id.tv_date);
            imgPoster = (ImageView)itemView.findViewById(R.id.img_poster);
        }
    }

}

