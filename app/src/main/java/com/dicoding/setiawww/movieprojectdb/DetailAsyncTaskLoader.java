package com.dicoding.setiawww.movieprojectdb;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.dicoding.setiawww.movieprojectdb.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by setiawww on 9/27/2017.
 */

public class DetailAsyncTaskLoader extends AsyncTaskLoader<ArrayList<DetailItems>> {
    private ArrayList<DetailItems> mData;
    public boolean hasResult = false;
    private String movieID;

    public DetailAsyncTaskLoader(Context context, String id) {
        super(context);
        onContentChanged();
        movieID = id;                           // id
        Log.d("INIT DETAIL ASYNCLOADER","1");
    }

    @Override
    protected void onStartLoading() {
        Log.d("Detail Content Changed","1");
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<DetailItems> data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    private static String API_KEY = THE_MOVIE_DB_API_KEY;

    @Override
    public ArrayList<DetailItems> loadInBackground() {
        Log.d("DETAIL LOAD BG","1");
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<DetailItems> detailMovie = new ArrayList<>();

        String url = "https://api.themoviedb.org/3/movie/"+ movieID +"?api_key="+ API_KEY +"&language=en-US";

        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);

                    DetailItems movieItem = new DetailItems(responseObject);
                    detailMovie.add(movieItem);

                    Log.d("DETAIL REQUEST SUCCESS","1");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("DETAIL REQUEST FAILED","1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
                Log.d("DETAIL onFAILURE", "1");
            }
        });

        Log.d("DETAIL JUDUL",detailMovie.get(0).getJudul());

        Log.d("DETAIL BEFORE RETURN","1");

        return detailMovie;
    }

    protected void onReleaseResources(ArrayList<DetailItems> data) {
        //nothing to do.
    }

    public ArrayList<DetailItems> getResult() {
        return mData;
    }
}
