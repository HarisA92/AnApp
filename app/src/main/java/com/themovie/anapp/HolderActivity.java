package com.themovie.anapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class HolderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        TextView title = findViewById(R.id.text_title);
        TextView overview = findViewById(R.id.text_overview);
        ImageView image = findViewById(R.id.image_poster);

        if(getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        String titleMovie = getIntent().getStringExtra(getResources().getString(R.string.title));
        String imageMovie = getIntent().getStringExtra(getResources().getString(R.string.backdropPath));
        String overviewMovie = getIntent().getStringExtra(getResources().getString(R.string.overview));

        title.setText(titleMovie);
        overview.setText(overviewMovie);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + imageMovie).into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
