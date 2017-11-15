package com.dicoding.setiawww.movieprojectdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.dicoding.setiawww.movieprojectdb.CustomOnItemClickListener;
import com.dicoding.setiawww.movieprojectdb.DetailActivity;
import com.dicoding.setiawww.movieprojectdb.MainActivity;
import com.dicoding.setiawww.movieprojectdb.Movie;
import com.dicoding.setiawww.movieprojectdb.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

//import com.bumptech.glide.Glide;

/**
 * Created by setiawww on 6/10/2017.
 */


public class CardViewMovieAdapter extends RecyclerView.Adapter<CardViewMovieAdapter.CardViewViewHolder>{
    private ArrayList<Movie> listMovie;
    private Context context;
    private android.support.v4.app.Fragment fragmentOrigin;
    private int tabOrigin;

    //public CardViewMovieAdapter(Context context) {
    //    this.context = context;
    //}

    public CardViewMovieAdapter(Fragment fragment, int tabPosition) {
        this.context = fragment.getContext();
        fragmentOrigin = fragment;
        tabOrigin = tabPosition;
    }

    public ArrayList<Movie> getListMovie() {
        return listMovie;
    }

    public void setListMovie(ArrayList<Movie> listMovie) {
        this.listMovie = listMovie;
    }
    @Override
    public CardViewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);
        CardViewViewHolder viewHolder = new CardViewViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CardViewViewHolder holder, int position) {

        Movie movie = getListMovie().get(position);

        //Glide.with(context)
        //        .load("http://image.tmdb.org/t/p/w342"+movie.getPoster())
        //        .override(350, 550)
        //        .into(holder.imgPoster);

        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+(movie.getPoster())).into(holder.imgPoster);

        holder.tvJudul.setText(movie.getJudul());
        holder.tvOverview.setText(movie.getOverview());
        holder.tvTanggal.setText(movie.getTanggal());

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Detail MovieID: ", getListMovie().get(position).getId());

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, getListMovie().get(position).getId());
                detailIntent.putExtra(DetailActivity.EXTRA_TAB, tabOrigin);
                //detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(detailIntent);
                ((MainActivity)context).startActivityForResult(detailIntent, DetailActivity.REQUEST_DETAIL);
                //fragmentOrigin.startActivityForResult(detailIntent, DetailActivity.REQUEST_DETAIL);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Share : ", getListMovie().get(position).getJudul());

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getListMovie().get(position).getJudul() + "\n" + getListMovie().get(position).getOverview());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Movie Info");
                sendIntent.setType("text/plain");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
        }));
    }

    @Override
    public int getItemCount() {
        return getListMovie().size();
    }

    public class CardViewViewHolder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvJudul, tvOverview, tvTanggal;
        Button btnDetail, btnShare;
        public CardViewViewHolder(View itemView) {
            super(itemView);
            imgPoster = (ImageView)itemView.findViewById(R.id.img_poster);
            tvJudul = (TextView)itemView.findViewById(R.id.tv_judul);
            tvOverview = (TextView)itemView.findViewById(R.id.tv_overview);
            tvTanggal = (TextView)itemView.findViewById(R.id.tv_tanggal);
            btnDetail = (Button)itemView.findViewById(R.id.btn_detail);
            btnShare = (Button)itemView.findViewById(R.id.btn_share);
        }
    }
}