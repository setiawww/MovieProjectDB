package com.dicoding.setiawww.movieprojectdb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.dicoding.setiawww.movieprojectdb.adapter.TabFragmentPagerAdapter;
import com.dicoding.setiawww.movieprojectdb.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private EditText movie_title;
    private ViewPager pager;
    private TabLayout tabs;
    private AlarmReceiver alarmReceiver;
    private SchedulerTask schedulerTask;
    private TabFragmentPagerAdapter adapter;
    private int REQUEST_CODE = 400;
    public boolean daily_notif_setting = true;
    public boolean upcoming_notif_setting = true;

    private SearchFragment searchFragment = new SearchFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Snackbar snackbar = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE);
                Snackbar.SnackbarLayout layout = (Snackbar.SnackbarLayout) snackbar.getView();

                // Inflate your custom view with an Edit Text
                LayoutInflater objLayoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View snackView = objLayoutInflater.inflate(R.layout.custom_snack, null); // custom_snac_layout is your custom xml

                movie_title = snackView.findViewById(R.id.tv_search);

                snackbar.setAction(getResources().getString(R.string.search), new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.d("Search : ", String.valueOf(movie_title.getText()));

                        if(searchFragment.isVisible())
                        {
                            searchFragment.searchMovie(String.valueOf(movie_title.getText()));
                        }
                        else {

                            FragmentManager fragmentManager = getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

                            Bundle bundle = new Bundle();
                            bundle.putString(searchFragment.TITLE_TO_SEARCH, String.valueOf(movie_title.getText()));
                            searchFragment.setArguments(bundle);
                            fragmentTransaction.replace(R.id.app_bar_main_layout, searchFragment, SearchFragment.class.getSimpleName());
                            fragmentTransaction.addToBackStack(null);
                            fragmentTransaction.commit();
                        }
                        hideSoftKeyboard(MainActivity.this);
                    }
                });
                layout.addView(snackView, 0);
                layout.setBackgroundColor(ContextCompat.getColor(getApplicationContext(),R.color.cardview_light_background));
                snackbar.show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //inisialisasi tab dan pager
        View main_content = findViewById(R.id.app_bar_main_layout).findViewById(R.id.content_main_layout);

        pager = main_content.findViewById(R.id.pager);
        tabs = main_content.findViewById(R.id.tabs);
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), this);

        //set object adapter kedalam ViewPager
        pager.setAdapter(adapter);

        //Manipulasi sedikit untuk set TextColor pada Tab
        tabs.setTabTextColors(getResources().getColor(R.color.colorPrimaryDark),
                getResources().getColor(android.R.color.white));

        //set tab ke ViewPager
        tabs.setupWithViewPager(pager);

        //konfigurasi Gravity Fill untuk Tab berada di posisi yang proposional
        tabs.setTabGravity(TabLayout.GRAVITY_FILL);

        // set reminder
        alarmReceiver = new AlarmReceiver();
        schedulerTask = new SchedulerTask(this);

        if(savedInstanceState == null)
        {
            // default reminder
            daily_notif_setting = true;
            upcoming_notif_setting = true;
            alarmReceiver.setDailyAlarm(this, "14:05");
            schedulerTask.createPeriodicTask();
        }

    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingInt = new Intent(this, SettingActivity.class);
            settingInt.putExtra(SettingActivity.EXTRA_DAILY_SETTING, daily_notif_setting);
            settingInt.putExtra(SettingActivity.EXTRA_UPCOMING_SETTING, upcoming_notif_setting);
            startActivityForResult(settingInt, REQUEST_CODE);
            //startActivity(settingInt);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fragmentManager = getSupportFragmentManager();

        if(id == R.id.nav_nowshowing){
            if(searchFragment.isVisible()){
                fragmentManager.beginTransaction().remove(searchFragment).commit();
            }
            pager.setCurrentItem(0);
        }
        else if(id == R.id.nav_upcoming){
            if(searchFragment.isVisible()){
                fragmentManager.beginTransaction().remove(searchFragment).commit();
            }
            pager.setCurrentItem(1);
        }
        else if(id == R.id.nav_favourite){
            if(searchFragment.isVisible()){
                fragmentManager.beginTransaction().remove(searchFragment).commit();
            }
            pager.setCurrentItem(2);
        }
        else if(id == R.id.nav_manage){
            Intent settingIntent = new Intent(MainActivity.this, SettingActivity.class);

            settingIntent.putExtra(SettingActivity.EXTRA_DAILY_SETTING, daily_notif_setting);
            settingIntent.putExtra(SettingActivity.EXTRA_UPCOMING_SETTING, upcoming_notif_setting);

            startActivityForResult(settingIntent, REQUEST_CODE);
            //startActivity(settingIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("MainActivity: ", "onActivityResult");
        if (requestCode == REQUEST_CODE){
            if (resultCode == SettingActivity.RESULT_CODE){
                daily_notif_setting = data.getBooleanExtra(SettingActivity.EXTRA_DAILY_SETTING, true);
                upcoming_notif_setting = data.getBooleanExtra(SettingActivity.EXTRA_UPCOMING_SETTING, true);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("dailyNotif", daily_notif_setting);
        outState.putBoolean("upcomingNotif", upcoming_notif_setting);
        Log.d("MainActivity :", "onSaveInstanceState");
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        daily_notif_setting = savedInstanceState.getBoolean("dailyNotif");
        upcoming_notif_setting = savedInstanceState.getBoolean("upcomingNotif");
        Log.d("MainActivity :", "onRestoreInstanceState");
    }

}
