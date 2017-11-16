package com.dicoding.setiawww.movieprojectdb.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
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
import com.dicoding.setiawww.movieprojectdb.R;
import com.dicoding.setiawww.movieprojectdb.entity.Favourite;
import com.dicoding.setiawww.movieprojectdb.fragment.FavouriteFragment;
import com.squareup.picasso.Picasso;

import java.util.LinkedList;

import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.CONTENT_URI;

/**
 * Created by setiawww on 10/11/2017.
 */

public class FaveAdapter extends RecyclerView.Adapter<FaveAdapter.FaveViewholder>{
    //private LinkedList<Favourite> listFaves;
    private Context context;
    private FavouriteFragment favouriteFragment;
    private Cursor listFaves;

    //public FaveAdapter(Context context) {
    //    this.context = context;
    //}

    public FaveAdapter(FavouriteFragment fragment){
        this.context = fragment.getContext();
        favouriteFragment = fragment;
    }
/*
    public LinkedList<Favourite> getListFaves() {
        return listFaves;
    }

    public void setListFaves(LinkedList<Favourite> listFaves) {
        this.listFaves = listFaves;
    }
*/
    public void setListFaves(Cursor listFaves) {
        this.listFaves = listFaves;
    }

    @Override
    public FaveViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);

        return new FaveViewholder(view);
    }

    @Override
    public void onBindViewHolder(FaveViewholder holder, int position) {
        //Glide.with(context)
        //        .load("http://image.tmdb.org/t/p/w342"+getListFaves().get(position).getPoster())
        //        .override(350, 550)
        //        .into(holder.imgPoster);
/*
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+(getListFaves().get(position).getPoster())).into(holder.imgPoster);

        holder.tvJudul.setText(getListFaves().get(position).getJudul());
        holder.tvOverview.setText(getListFaves().get(position).getOverview());
        holder.tvTanggal.setText(getListFaves().get(position).getDate());

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Detail MovieID: ", getListFaves().get(position).getIdmovie());

                Intent detailIntent = new Intent(context, DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_POSITION, position);
                detailIntent.putExtra(DetailActivity.EXTRA_FAVE, getListFaves().get(position));
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, getListFaves().get(position).getIdmovie());
                //detailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //context.startActivity(detailIntent);
                favouriteFragment.startActivityForResult(detailIntent, DetailActivity.REQUEST_DETAIL);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {

            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Share : ", getListFaves().get(position).getJudul());

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, getListFaves().get(position).getJudul() + "\n" + getListFaves().get(position).getOverview());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Movie Info");
                sendIntent.setType("text/plain");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
        }));
*/
        final Favourite favourite = getItem(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+(favourite.getPoster())).into(holder.imgPoster);
        holder.tvJudul.setText(favourite.getJudul());
        holder.tvOverview.setText(favourite.getOverview());
        holder.tvTanggal.setText(favourite.getDate());

        holder.btnDetail.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Detail MovieID: ", favourite.getIdmovie());

                Intent detailIntent = new Intent(context, DetailActivity.class);
                Uri uri = Uri.parse(CONTENT_URI+"/"+favourite.getId());
                detailIntent.setData(uri);
                detailIntent.putExtra(DetailActivity.EXTRA_POSITION, position);
                detailIntent.putExtra(DetailActivity.EXTRA_FAVE, favourite);
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, favourite.getIdmovie());
                favouriteFragment.startActivityForResult(detailIntent, DetailActivity.REQUEST_DETAIL);
            }
        }));

        holder.btnShare.setOnClickListener(new CustomOnItemClickListener(position, new CustomOnItemClickListener.OnItemClickCallback() {
            @Override
            public void onItemClicked(View view, int position) {
                Log.d("Share : ", favourite.getJudul());

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, favourite.getJudul() + "\n" + favourite.getOverview());
                sendIntent.putExtra(Intent.EXTRA_TITLE, "Movie Info");
                sendIntent.setType("text/plain");
                sendIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(sendIntent);
            }
        }));
    }

    @Override
    //public int getItemCount() {
    //    return getListFaves().size();
    //}

    public int getItemCount() {
        if (listFaves == null) return 0;
        return listFaves.getCount();
    }

    private Favourite getItem(int position){
        if (!listFaves.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new Favourite(listFaves);
    }

    public class FaveViewholder extends RecyclerView.ViewHolder{
        ImageView imgPoster;
        TextView tvJudul, tvOverview, tvTanggal;
        Button btnDetail, btnShare;

        public FaveViewholder(View itemView) {
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
