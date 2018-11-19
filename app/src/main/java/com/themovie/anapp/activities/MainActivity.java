package com.themovie.anapp.activities;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;

import com.themovie.anapp.R;
import com.themovie.anapp.adapters.TabLayoutAdapter;
import com.themovie.anapp.fragments.MovieFragment;
import com.themovie.anapp.fragments.TvShowFragment;

public class MainActivity extends AppCompatActivity implements MovieFragment.OnFragmentInteractionListener, TvShowFragment.OnFragmentInteractionListener {

    private String getTabNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ViewPager viewPager = findViewById(R.id.pager);
        TabLayout tabLayout = findViewById(R.id.tablayout);
        tabLayout.addTab(tabLayout.newTab().setText("Movies"));
        tabLayout.addTab(tabLayout.newTab().setText("TV Shows"));

        tabLayout.setTabMode(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);

        tabLayout.setTabMode(TabLayout.GRAVITY_FILL);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);


        TabLayoutAdapter adapter = new TabLayoutAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_item_search, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
