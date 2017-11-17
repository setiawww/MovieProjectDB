package com.dicoding.setiawww.myfavouritemovie.adapter;

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

import com.dicoding.setiawww.myfavouritemovie.MainActivityFragment;
import com.dicoding.setiawww.myfavouritemovie.R;
import com.dicoding.setiawww.myfavouritemovie.entity.FaveItem;
import com.squareup.picasso.Picasso;

/**
 * Created by setiawww on 17/11/2017.
 */

public class FaveAdapter extends RecyclerView.Adapter<FaveAdapter.FaveViewholder>{

    private Context context;
    private MainActivityFragment favouriteFragment;
    private Cursor listFaves;

    public FaveAdapter(MainActivityFragment fragment){
        this.context = fragment.getContext();
        favouriteFragment = fragment;
    }

    public void setListFaves(Cursor listFaves) {
        this.listFaves = listFaves;
    }

    @Override
    public FaveAdapter.FaveViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_movie, parent, false);

        return new FaveViewholder(view);
    }

    @Override
    public void onBindViewHolder(FaveAdapter.FaveViewholder holder, int position) {
        final FaveItem favourite = getItem(position);
        Picasso.with(context).load("http://image.tmdb.org/t/p/w342"+(favourite.getPoster())).into(holder.imgPoster);
        holder.tvJudul.setText(favourite.getJudul());
        holder.tvOverview.setText(favourite.getOverview());
        holder.tvTanggal.setText(favourite.getDate());
/*
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
  */
    }

    @Override
    public int getItemCount() {
        if (listFaves == null) return 0;
        return listFaves.getCount();
    }

    private FaveItem getItem(int position){
        if (!listFaves.moveToPosition(position)) {
            throw new IllegalStateException("Position invalid");
        }
        return new FaveItem(listFaves);
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
