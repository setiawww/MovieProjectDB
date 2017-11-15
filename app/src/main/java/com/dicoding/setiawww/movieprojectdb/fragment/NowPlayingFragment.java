package com.dicoding.setiawww.movieprojectdb.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.setiawww.movieprojectdb.Movie;
import com.dicoding.setiawww.movieprojectdb.MovieAsyncTaskLoader;
import com.dicoding.setiawww.movieprojectdb.R;
import com.dicoding.setiawww.movieprojectdb.adapter.CardViewMovieAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private RecyclerView rvNowPlaying;
    private CardViewMovieAdapter adapter;

    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View nowPlayingView = inflater.inflate(R.layout.fragment_now_playing,container,false);

        rvNowPlaying = nowPlayingView.findViewById(R.id.rv_nowplaying);
        rvNowPlaying.setHasFixedSize(true);

        //adapter = new CardViewMovieAdapter(getActivity());                  //
        adapter = new CardViewMovieAdapter(this, 0);                  //

        //getLoaderManager().restartLoader(0, null, );
        getLoaderManager().initLoader(0, null, this);

        return nowPlayingView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int i, Bundle bundle) {
        Log.d("Create loader","1");

        return new MovieAsyncTaskLoader(getContext(), MovieAsyncTaskLoader.MOVIE_NOWPLAYING_DATA_TYPE, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> movies) {
        Log.d("Load Finish","1");

        rvNowPlaying.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter.setListMovie(movies);
        rvNowPlaying.setAdapter(adapter);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.d("Load Reset","1");
    }
}
