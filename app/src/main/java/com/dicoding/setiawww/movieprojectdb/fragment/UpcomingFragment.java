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
public class UpcomingFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private RecyclerView rvUpcoming;
    private CardViewMovieAdapter adapter;

    public UpcomingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View upcomingView = inflater.inflate(R.layout.fragment_upcoming,container,false);

        rvUpcoming = upcomingView.findViewById(R.id.rv_upcoming);
        rvUpcoming.setHasFixedSize(true);

        //adapter = new CardViewMovieAdapter(getActivity());          //
        adapter = new CardViewMovieAdapter(this, 1);          //

        //getLoaderManager().restartLoader(0, null, );
        getLoaderManager().initLoader(0, null, this);

        return upcomingView;
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        //return null;
        Log.d("Create loader","2");

        return new MovieAsyncTaskLoader(getContext(), MovieAsyncTaskLoader.MOVIE_UPCOMING_DATA_TYPE, "");
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, ArrayList<Movie> data) {
        Log.d("Load Finish","2");
        //progressBarLoading.setVisibility(View.GONE);
        rvUpcoming.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        adapter.setListMovie(data);
        rvUpcoming.setAdapter(adapter);

    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.d("Load Reset","2");
    }

}
