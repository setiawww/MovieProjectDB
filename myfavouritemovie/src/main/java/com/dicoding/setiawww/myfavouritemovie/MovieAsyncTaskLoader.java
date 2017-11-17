package com.dicoding.setiawww.myfavouritemovie;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static com.dicoding.setiawww.myfavouritemovie.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by setiawww on 6/10/2017.
 */

public class MovieAsyncTaskLoader extends AsyncTaskLoader<ArrayList<Movie>> {
    private ArrayList<Movie> mData;
    public boolean hasResult = false;
    private String titleToSearch;
    public static int MOVIE_NOWPLAYING_DATA_TYPE = 1;
    public static int MOVIE_UPCOMING_DATA_TYPE = 2;
    public static int MOVIE_SEARCH_DATA_TYPE = 3;
    private int category;
    private String url;

    public MovieAsyncTaskLoader(final Context context, int dataCategory, String judul) {
        super(context);
        onContentChanged();
        titleToSearch = judul;                  //
        category = dataCategory;

        Log.d("INIT ASYNCLOADER","1");
    }

    @Override
    protected void onStartLoading() {
        Log.d("Content Changed","1");
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<Movie> data) {
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
    public ArrayList<Movie> loadInBackground() {
        Log.d("LOAD BG","1");
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<Movie> movieItemses = new ArrayList<>();
        //String url;
        if(category == MOVIE_SEARCH_DATA_TYPE){
            url = "https://api.themoviedb.org/3/search/movie?api_key="+API_KEY+"&language=en-US&query="+titleToSearch;
        }
        else if(category == MOVIE_UPCOMING_DATA_TYPE) {
            url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+API_KEY+"&language=en-US";
        }
        else if(category == MOVIE_NOWPLAYING_DATA_TYPE) {
            url = "https://api.themoviedb.org/3/movie/now_playing?api_key="+API_KEY+"&language=en-US";
        }

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

                    JSONArray results = responseObject.getJSONArray("results");

                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie movieItems = new Movie(movie);
                        movieItemses.add(movieItems);
                    }
                    Log.d("REQUEST SUCCESS","1");
                }catch (Exception e){
                    e.printStackTrace();
                    Log.d("REQUEST FAILED","1");
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers,
                                  byte[] responseBody, Throwable error) {
            }
        });

        for (int i = 0 ; i< movieItemses.size() ; i++){
            Log.d("JUDUL",movieItemses.get(i).getJudul());
        }
        Log.d("BEFORE RETURN","1");
        return movieItemses;
    }

    protected void onReleaseResources(ArrayList<Movie> data) {
        //nothing to do.
    }

    public ArrayList<Movie> getResult() {
        return mData;
    }
}

