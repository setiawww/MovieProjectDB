package com.dicoding.setiawww.myfavouritemovie;

import android.content.Intent;
import android.database.Cursor;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dicoding.setiawww.myfavouritemovie.adapter.FaveAdapter;

import static com.dicoding.setiawww.myfavouritemovie.db.DatabaseContract.CONTENT_URI;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private RecyclerView rvFavourite;
    private FaveAdapter adapter;
    //private FaveHelper faveHelper;

    private Cursor list;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return inflater.inflate(R.layout.fragment_main, container, false);
        final View mainFragment = inflater.inflate(R.layout.fragment_main, container, false);

        rvFavourite = mainFragment.findViewById(R.id.rv_favourite);
        rvFavourite.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));
        rvFavourite.setHasFixedSize(true);

        adapter = new FaveAdapter(this);

        adapter.setListFaves(list);
        rvFavourite.setAdapter(adapter);

        new LoadFaveAsync().execute();

        return mainFragment;
    }

    private class LoadFaveAsync extends AsyncTask<Void, Void, Cursor> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //progressBar.setVisibility(View.VISIBLE);

        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            return getActivity().getContentResolver().query(CONTENT_URI,null,null,null,null);

        }

        @Override
        protected void onPostExecute(Cursor faves) {
            super.onPostExecute(faves);
            //progressBar.setVisibility(View.GONE);

            list = faves;
            adapter.setListFaves(list);
            adapter.notifyDataSetChanged();

            if (list.getCount() == 0){
                Snackbar.make(rvFavourite, "No movie on the list", Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
/*
        if (requestCode == DetailActivity.REQUEST_DETAIL) {
            if (resultCode == DetailActivity.RESULT_ADD) {
                new LoadFaveAsync().execute();
            } else if (resultCode == DetailActivity.RESULT_DELETE) {
                new LoadFaveAsync().execute();
            }
        }
*/
    }

}
