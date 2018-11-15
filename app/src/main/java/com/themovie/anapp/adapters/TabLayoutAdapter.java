package com.themovie.anapp.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.themovie.anapp.fragments.MovieFragment;
import com.themovie.anapp.fragments.TvShowFragment;

public class TabLayoutAdapter extends FragmentStatePagerAdapter {

    private int numberOfTabs;

    public TabLayoutAdapter( final FragmentManager fm, int numberOfTabs) {
        super(fm);
        this.numberOfTabs = numberOfTabs;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MovieFragment();
            case 1:
                return new TvShowFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTabs;
    }
}
