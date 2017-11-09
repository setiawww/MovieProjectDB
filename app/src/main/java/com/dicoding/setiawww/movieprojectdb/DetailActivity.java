package com.dicoding.setiawww.movieprojectdb;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<DetailItems>> {

    private TextView tvJudul, tvDetail;
    private ImageView ivPoster;
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private String movieID;
    private String judul;
    private String overview;
    private String tanggal;
    private String poster;
    private String status;
    private ProgressBar progressBarDetail;
    private final String MOVIE_ID_TO_SHOW = "movie_id_to_show";
    private Button buttonStar;
    private Boolean starFave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        progressBarDetail = (ProgressBar) findViewById(R.id.progressBarDetail);
        tvJudul = (TextView) findViewById(R.id.tv_Judul);
        tvDetail = (TextView) findViewById(R.id.tv_detail);
        ivPoster = (ImageView) findViewById(R.id.iv_Poster);
        ivPoster.setVisibility(View.INVISIBLE);
        tvJudul.setVisibility(View.INVISIBLE);
        tvDetail.setVisibility(View.INVISIBLE);

        buttonStar = findViewById(R.id.buttonStar);
        buttonStar.setVisibility(View.INVISIBLE);

        buttonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(starFave){
                    starFave = false;
                    buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_off));

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.remove_from_favourite), Toast.LENGTH_SHORT).show();
                }
                else {
                    starFave = true;
                    buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_on));

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_to_favourite), Toast.LENGTH_SHORT).show();
                }
            }
        });

        Bundle movie = new Bundle();
        movie.putString(MOVIE_ID_TO_SHOW, getIntent().getStringExtra(EXTRA_MOVIE_ID));
        getLoaderManager().initLoader(1, movie, this);
    }


    @Override
    public Loader<ArrayList<DetailItems>> onCreateLoader(int i, Bundle bundle) {
        Log.d("Create Detail Loader", "1");
        movieID = bundle.getString(MOVIE_ID_TO_SHOW);
        return new DetailAsyncTaskLoader(this, movieID);  //
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<DetailItems>> loader, ArrayList<DetailItems> data) {
        Log.d("Detail Load Finish", "1");
        ivPoster.setVisibility(View.VISIBLE);
        tvJudul.setVisibility(View.VISIBLE);
        tvDetail.setVisibility(View.VISIBLE);
        progressBarDetail.setVisibility(View.GONE);

        buttonStar.setVisibility(View.VISIBLE);

        judul = data.get(0).getJudul();
        overview = data.get(0).getDetail();
        tanggal = data.get(0).getDate();
        poster = data.get(0).getPoster();
        status = data.get(0).getStatus();

        tvJudul.setText(judul);
        tvDetail.setText(overview + "\n\nRelease date: " + tanggal + "\n\nStatus: " + status);
        Picasso.with(this).load("http://image.tmdb.org/t/p/w342" + poster).into(ivPoster);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<DetailItems>> loader) {
        Log.d("Detail Load Reset", "1");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("DetailActivity: ", "onDestroy");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            share();
            return true;
        }

        if (id == android.R.id.home) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, judul + "\n" + overview);
        sendIntent.putExtra(Intent.EXTRA_TITLE, "Movie Info");
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
