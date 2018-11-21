package com.themovie.anapp.retrofit.model.modelMovie;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TopRatedMovies {

    @SerializedName("results")
    @Expose
    private List<MovieResult> results = new ArrayList<>();

    public List<MovieResult> getResults() {
        return results;
    }
}
