package com.dicoding.setiawww.movieprojectdb.fragment;


import android.content.Intent;
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
import android.widget.ProgressBar;

import com.dicoding.setiawww.movieprojectdb.DetailActivity;
import com.dicoding.setiawww.movieprojectdb.ItemClickSupport;
import com.dicoding.setiawww.movieprojectdb.Movie;
import com.dicoding.setiawww.movieprojectdb.MovieAsyncTaskLoader;
import com.dicoding.setiawww.movieprojectdb.R;
import com.dicoding.setiawww.movieprojectdb.adapter.ListMovieAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<Movie>> {

    private RecyclerView rvSearch;
    private String title, judul;
    public final String TITLE_TO_SEARCH = "title_to_search";
    private ProgressBar progressBarSearch;

    public SearchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View searchView = inflater.inflate(R.layout.fragment_search,container,false);

        rvSearch = searchView.findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);

        title = getArguments().getString(TITLE_TO_SEARCH);

        progressBarSearch = searchView.findViewById(R.id.progressBarSearch);

        searchMovie(title);

        return searchView;
    }

    public void searchMovie(String judul)
    {
        rvSearch.removeAllViewsInLayout();
        rvSearch.setVisibility(View.INVISIBLE);

        Bundle searchTitle = new Bundle();
        searchTitle.putString(TITLE_TO_SEARCH, judul);
        getLoaderManager().restartLoader(0, searchTitle, this);
        progressBarSearch.setVisibility(View.VISIBLE);
    }

    @Override
    public Loader<ArrayList<Movie>> onCreateLoader(int id, Bundle args) {
        //return null;
        Log.d("Create loader","3");

        judul = args.getString(TITLE_TO_SEARCH);
        return new MovieAsyncTaskLoader(getContext(), MovieAsyncTaskLoader.MOVIE_SEARCH_DATA_TYPE, judul);
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Movie>> loader, final ArrayList<Movie> movies) {

        Log.d("Load Finish","3");
        progressBarSearch.setVisibility(View.GONE);
        rvSearch.setVisibility(View.VISIBLE);

        rvSearch.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        ListMovieAdapter listMovieAdapter = new ListMovieAdapter(getActivity().getApplicationContext());
        listMovieAdapter.setListMovie(movies);
        rvSearch.setAdapter(listMovieAdapter);

        ItemClickSupport.addTo(rvSearch).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {

                Movie movie = movies.get(position);
                String movieID = movie.getId();

                Log.d("Selected MovieID: ", String.valueOf(movieID));

                Intent detailIntent = new Intent(getContext(), DetailActivity.class);
                detailIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieID);
                startActivity(detailIntent);
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Movie>> loader) {
        Log.d("Load Reset","3");
    }

}
