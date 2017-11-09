package com.dicoding.setiawww.movieprojectdb;

import android.content.Context;

import com.google.android.gms.gcm.GcmNetworkManager;
import com.google.android.gms.gcm.PeriodicTask;
import com.google.android.gms.gcm.Task;

/**
 * Created by setiawww on 25/9/2017.
 */

public class SchedulerTask {

    private GcmNetworkManager mGcmNetworkManager;

    public SchedulerTask(Context context){
        mGcmNetworkManager = GcmNetworkManager.getInstance(context);
    }

    public void createPeriodicTask() {
        Task periodicTask = new PeriodicTask.Builder()
                .setService(SchedulerService.class)
                .setPeriod(/*60*/21600)                       // 6 X 60 X 60 = 6HRS
                .setFlex(10)
                .setTag(SchedulerService.TAG_TASK_MOVIE_LOG)
                .setPersisted(true)
                .build();
        mGcmNetworkManager.schedule(periodicTask);
    }

    public void cancelPeriodicTask(){
        if (mGcmNetworkManager != null){
            mGcmNetworkManager.cancelTask(SchedulerService.TAG_TASK_MOVIE_LOG, SchedulerService.class);
        }
    }
}
