package com.dicoding.setiawww.movieprojectdb.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.dicoding.setiawww.movieprojectdb.R;
import com.dicoding.setiawww.movieprojectdb.fragment.FavouriteFragment;
import com.dicoding.setiawww.movieprojectdb.fragment.NowPlayingFragment;
import com.dicoding.setiawww.movieprojectdb.fragment.UpcomingFragment;

/**
 * Created by setiawww on 4/10/2017.
 */

public class TabFragmentPagerAdapter extends FragmentPagerAdapter {
    //nama tab nya
    private Context context;

    String[] title = new String[]{
            "Now Playing", "Upcoming", "Favourite"
    };

    public TabFragmentPagerAdapter(FragmentManager fm, Context mContext) {
        super(fm);
        context = mContext;
    }

    //method ini yang akan memanipulasi penampilan Fragment dilayar
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position){
            case 0:
                fragment = new NowPlayingFragment();
                break;
            case 1:
                fragment = new UpcomingFragment();
                break;
            case 2:
                fragment = new FavouriteFragment();
                break;
            default:
                fragment = null;
                break;
        }

        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        //return title[position];

        String tabTitle = "";
        switch(position)
        {
            case 0:
                tabTitle = context.getResources().getString(R.string.now_playing);
                break;
            case 1:
                tabTitle = context.getResources().getString(R.string.upcoming);
                break;
            case 2:
                tabTitle = context.getResources().getString(R.string.favourite);
                break;
        }
        return tabTitle;

    }

    @Override
    public int getCount() {
        return title.length;
    }
}
