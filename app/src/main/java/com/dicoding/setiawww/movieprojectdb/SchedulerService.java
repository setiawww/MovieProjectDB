package com.dicoding.setiawww.movieprojectdb;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.GcmTaskService;
import com.google.android.gms.gcm.TaskParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Random;

import cz.msebera.android.httpclient.Header;

import static com.dicoding.setiawww.movieprojectdb.BuildConfig.THE_MOVIE_DB_API_KEY;

/**
 * Created by setiawww on 25/9/2017.
 */

public class SchedulerService extends GcmTaskService {
    public static final String TAG = "GetMovie";
    private final String APP_ID = THE_MOVIE_DB_API_KEY;
    public static String TAG_TASK_MOVIE_LOG = "MovieTask";
    private final int NOTIF_ID_UPCOMING = 107;

    @Override
    public int onRunTask(TaskParams taskParams) {
        int result = 0;
        if (taskParams.getTag().equals(TAG_TASK_MOVIE_LOG)){
            getUpcomingMovie();
            result = GcmNetworkManager.RESULT_SUCCESS;
        }
        return result;
    }

    private void getUpcomingMovie(){
        Log.d("GetMovie", "Running");
        SyncHttpClient client = new SyncHttpClient();
        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key="+APP_ID+"&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                String result = new String(responseBody);
                Log.d(TAG, result);
                try {
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");

                    //String total_results = responseObject.getString("total_results");   // test

                    Random random = new Random();
                    int random_index = random.nextInt(results.length());

                    String judul = responseObject.getJSONArray("results").getJSONObject(random_index).getString("title");
                    String tanggal = responseObject.getJSONArray("results").getJSONObject(random_index).getString("release_date");
                    String movieID = responseObject.getJSONArray("results").getJSONObject(random_index).getString("id");
                    String title = judul;
                    String message = "Release date: " + tanggal;

                    int notifId = NOTIF_ID_UPCOMING;
                    showNotification(getApplicationContext(), title, message, movieID, notifId);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.d("GetMovie", "Failed");
            }
        });
    }

    @Override
    public void onInitializeTasks() {
        super.onInitializeTasks();
        SchedulerTask mSchedulerTask = new SchedulerTask(this);
        mSchedulerTask.createPeriodicTask();
    }

    private void showNotification(Context context, String title, String message, String movieID, int notifId){

        // Creates an Intent for the Activity
        Intent notifyIntent = new Intent(getApplicationContext(), DetailActivity.class);
        notifyIntent.putExtra(DetailActivity.EXTRA_MOVIE_ID, movieID);
        // Sets the Activity to start in a new, empty task
        notifyIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        // Creates the PendingIntent
        PendingIntent pendingIntent =
                PendingIntent.getActivity(
                        this,
                        0,
                        notifyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        NotificationManager notificationManagerCompat = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setContentTitle(title)
                .setSmallIcon(R.drawable.ic_local_movies_black_24dp)
                .setContentText(message)
                .setColor(ContextCompat.getColor(context, android.R.color.black))
                .setVibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .setSound(alarmSound)
                .setContentIntent(pendingIntent)        //
                .setAutoCancel(true);                   //
        notificationManagerCompat.notify(notifId, builder.build());
    }
}
