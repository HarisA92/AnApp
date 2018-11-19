package com.themovie.anapp.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.themovie.anapp.R;

public class HolderActivity extends AppCompatActivity {

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holder);

        TextView title = findViewById(R.id.text_title);
        TextView overview = findViewById(R.id.text_overview);
        ImageView image = findViewById(R.id.image_poster);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.centerCrop();

        String titleMovie = getIntent().getStringExtra(getResources().getString(R.string.title));
        String imageMovie = getIntent().getStringExtra(getResources().getString(R.string.backdropPath));
        String overviewMovie = getIntent().getStringExtra(getResources().getString(R.string.overview));

        title.setText(titleMovie);
        overview.setText(overviewMovie);
        Glide.with(this).load(getResources().getString(R.string.imagePath) + imageMovie).apply(requestOptions).into(image);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
