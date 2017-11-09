package com.dicoding.setiawww.movieprojectdb;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

public class SettingActivity extends AppCompatActivity {

    public boolean dailyNotification;
    public boolean upcomingNotification;
    private Switch dailySwitch;
    private Switch upcomingSwitch;
    public static String EXTRA_DAILY_SETTING = "extra_daily_setting";
    public static String EXTRA_UPCOMING_SETTING = "extra_upcoming_setting";
    public static int RESULT_CODE = 440;
    private AlarmReceiver alarmReceiver;
    private SchedulerTask mSchedulerTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        alarmReceiver = new AlarmReceiver();
        mSchedulerTask = new SchedulerTask(getApplicationContext());

        dailySwitch = (Switch) findViewById(R.id.switchDaily);

        dailySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(dailySwitch.isChecked()){
                    dailyNotification = true;
                    alarmReceiver.setDailyAlarm(getApplicationContext(), "14:05");
                }
                else{
                    dailyNotification = false;
                    alarmReceiver.cancelAlarm(getApplicationContext());
                }
            }
        });

        upcomingSwitch = (Switch) findViewById(R.id.switchUpcoming);

        upcomingSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(upcomingSwitch.isChecked()){
                    upcomingNotification = true;
                    mSchedulerTask.createPeriodicTask();
                }
                else{
                    upcomingNotification = false;
                    mSchedulerTask.cancelPeriodicTask();
                }
            }
        });

        Intent intent = getIntent();
        if(intent != null){
            dailyNotification = intent.getBooleanExtra(EXTRA_DAILY_SETTING, true);
            dailySwitch.setChecked(dailyNotification);
            upcomingNotification = intent.getBooleanExtra(EXTRA_UPCOMING_SETTING, true);
            upcomingSwitch.setChecked(upcomingNotification);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dailyNotif", dailyNotification);
        outState.putBoolean("upcomingNotif", upcomingNotification);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        dailyNotification = savedInstanceState.getBoolean("dailyNotif");
        upcomingNotification = savedInstanceState.getBoolean("upcomingNotif");
    }

    public void onClick(View v){
        switch (v.getId()) {
            case R.id.tvSettingLocale:
                Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
                startActivity(mIntent);
                break;
        }
    }

    @Override
    public void onBackPressed() {
        Intent resultIntent = new Intent();
        resultIntent.putExtra(EXTRA_DAILY_SETTING, dailyNotification);
        resultIntent.putExtra(EXTRA_UPCOMING_SETTING, upcomingNotification);
        setResult(RESULT_CODE, resultIntent);
        finish();
        super.onBackPressed();
    }
}
