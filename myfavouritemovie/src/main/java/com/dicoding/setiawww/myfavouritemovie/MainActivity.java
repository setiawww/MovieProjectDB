package com.dicoding.setiawww.myfavouritemovie;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.view.inputmethod.InputMethodManager;

public class MainActivity extends AppCompatActivity {

    private EditText movie_title;
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
                            fragmentTransaction.replace(R.id.fragment, searchFragment, SearchFragment.class.getSimpleName());
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }

}
