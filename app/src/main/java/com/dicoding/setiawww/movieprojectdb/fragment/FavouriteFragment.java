package com.dicoding.setiawww.movieprojectdb.fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.setiawww.movieprojectdb.Movie;
import com.dicoding.setiawww.movieprojectdb.R;
import com.dicoding.setiawww.movieprojectdb.adapter.FaveAdapter;
import com.dicoding.setiawww.movieprojectdb.db.FaveHelper;
import com.dicoding.setiawww.movieprojectdb.entity.Favourite;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FavouriteFragment extends Fragment {

    private RecyclerView rvFavourite;
    //private ArrayList<Movie> list;

    private LinkedList<Favourite> list;
    private FaveAdapter adapter;
    private FaveHelper faveHelper;

    public FavouriteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_favourite, container, false);
        final View favouriteView = inflater.inflate(R.layout.fragment_favourite,container,false);

        rvFavourite = favouriteView.findViewById(R.id.rv_favourite);
        rvFavourite.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvFavourite.setHasFixedSize(true);

        //getLoaderManager().restartLoader(0, null, );
        //getLoaderManager().initLoader(0, null, this);

        faveHelper = new FaveHelper(getActivity().getApplicationContext());
        try {
            faveHelper.open();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        list = new LinkedList<>();

        adapter = new FaveAdapter(getActivity());
        adapter.setListFaves(list);
        rvFavourite.setAdapter(adapter);

        new LoadFaveAsync().execute();

        return favouriteView;
    }

    private class LoadFaveAsync extends AsyncTask<Void, Void, ArrayList<Favourite>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

            if (list.size() > 0){
                list.clear();
            }
        }

        @Override
        protected ArrayList<Favourite> doInBackground(Void... voids) {
            return faveHelper.query();
        }

        @Override
        protected void onPostExecute(ArrayList<Favourite> faves) {
            super.onPostExecute(faves);
            //progressBar.setVisibility(View.GONE);

            list.addAll(faves);
            adapter.setListFaves(list);
            adapter.notifyDataSetChanged();

            if (list.size() == 0){
                Snackbar.make(rvFavourite, "No data available", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(faveHelper != null)
        {
            faveHelper.close();
        }
    }
}
