package com.dicoding.setiawww.movieprojectdb;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
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

import com.dicoding.setiawww.movieprojectdb.db.FaveHelper;
import com.dicoding.setiawww.movieprojectdb.entity.Favourite;
import com.squareup.picasso.Picasso;

import java.sql.SQLException;
import java.util.ArrayList;

import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.CONTENT_URI;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.DATE;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.IDMOVIE;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.JUDUL;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.OVERVIEW;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.POSTER;
import static com.dicoding.setiawww.movieprojectdb.db.DatabaseContract.FaveColumns.STATUS;

public class DetailActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<DetailItems>> {

    private TextView tvJudul, tvDetail;
    private ImageView ivPoster;
    public static final String EXTRA_MOVIE_ID = "extra_movie_id";
    private String movieID, idMovie;
    private String judul;
    private String overview;
    private String tanggal;
    private String poster;
    private String status;
    private ProgressBar progressBarDetail;
    private final String MOVIE_ID_TO_SHOW = "movie_id_to_show";
    private Button buttonStar;
    private Boolean starFave = false;

    public static String EXTRA_FAVE = "extra_fave";
    public static String EXTRA_POSITION = "extra_position";
    public static String EXTRA_TAB = "extra_tab";

    public static int REQUEST_DETAIL = 900;
    public static int REQUEST_ADD = 100;
    public static int RESULT_ADD = 101;
    public static int REQUEST_UPDATE = 200;
    public static int RESULT_UPDATE = 201;
    public static int RESULT_DELETE = 301;

    private Favourite favourite, fave;
    private int position, tabPosition;
    private FaveHelper faveHelper;
    private Uri uri;
    private Cursor cursor;

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

        Bundle movie = new Bundle();
        idMovie = getIntent().getStringExtra(EXTRA_MOVIE_ID);
        movie.putString(MOVIE_ID_TO_SHOW, idMovie);
        getLoaderManager().initLoader(1, movie, this);

        tabPosition = getIntent().getIntExtra(EXTRA_TAB,0);                             // from NowPlaying, Upcoming, Search

        faveHelper = new FaveHelper(this);
        try {
            faveHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //favourite = getIntent().getParcelableExtra(EXTRA_FAVE);

        //if (favourite != null){
        //    position = getIntent().getIntExtra(EXTRA_POSITION, 0);
        //}

        uri = getIntent().getData();                                                                // only from FavouriteFragment

        if (uri != null) {
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) {
                    favourite = new Favourite(cursor);
                }
                cursor.close();
            }
        }
        else {                                                                                      // from NowPlaying, Upcoming, Search
            //uri = Uri.parse(CONTENT_URI+"/"+idMovie);
            uri = Uri.parse(CONTENT_URI+"/"+IDMOVIE+"/"+idMovie);
            cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null){
                if(cursor.moveToFirst()) {
                    favourite = new Favourite(cursor);
                }
                cursor.close();
            }
        }

        if(favourite != null)
        {
            Log.d("Detail Movie: ", "Movie found in Favourite database!");
            starFave = true;
        }

        // Cari data movie di favourite database
        //ArrayList<Favourite> favourites = faveHelper.getDataByMovieID(idMovie);

        ////faveHelper.close();

        //if(favourites.size() != 0)
        //{
        //    Log.d("Detail Movie: ", "Found in Favourite database!");
        //    starFave = true;
        //    fave = favourites.get(0);
        //}

        buttonStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(starFave){
                    // remove from the favourite list
                    starFave = false;
                    buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_off));

                    //faveHelper.delete(favourite.getId());
                    //faveHelper.delete(fave.getId());

                    //Intent intent = new Intent();
                    //intent.putExtra(EXTRA_POSITION, position);
                    //setResult(RESULT_DELETE, intent);
                    //finish();

                    getContentResolver().delete(uri, null, null);

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.remove_from_favourite), Toast.LENGTH_SHORT).show();

                }
                else {
                    // add to the favourite list
                    starFave = true;
                    buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_on));

                    //Favourite newFave = new Favourite();
                    //newFave.setIdmovie(movieID);
                    //newFave.setJudul(judul);
                    //newFave.setOverview(overview);
                    //newFave.setDate(tanggal);
                    //newFave.setPoster(poster);
                    //newFave.setStatus(status);

                    //faveHelper.insert(newFave);

                    //setResult(RESULT_ADD);
                    //finish();

                    // Gunakan contentvalues untuk menampung data
                    ContentValues values = new ContentValues();
                    values.put(IDMOVIE, movieID);
                    values.put(JUDUL,judul);
                    values.put(OVERVIEW,overview);
                    values.put(DATE, tanggal);
                    values.put(POSTER, poster);
                    values.put(STATUS, status);

                    getContentResolver().insert(CONTENT_URI,values);

                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.added_to_favourite), Toast.LENGTH_SHORT).show();
                }
            }
        });


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
        if(starFave){
            buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_on));
        }
        else {
            buttonStar.setBackground(getResources().getDrawable(R.drawable.ic_star_off));
        }

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
        if (faveHelper != null){
            faveHelper.close();
        }
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

    public void onBackPressed() {
        if(starFave){
            Intent intent = new Intent();
            intent.putExtra(EXTRA_TAB, tabPosition);
            setResult(RESULT_ADD, intent);
        }
        else{
            Intent intent = new Intent();
            intent.putExtra(EXTRA_POSITION, position);
            intent.putExtra(EXTRA_TAB, tabPosition);
            setResult(RESULT_DELETE, intent);
        }
        finish();
        super.onBackPressed();
    }
}
