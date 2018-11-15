package com.themovie.anapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

        String titlemovie = getIntent().getStringExtra("position");
        String imagemovie = getIntent().getStringExtra("position3");
        String movieoverivew = getIntent().getStringExtra("position2");

        title.setText(titlemovie);
        overview.setText(movieoverivew);
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/" + imagemovie).into(image);

    }
}
