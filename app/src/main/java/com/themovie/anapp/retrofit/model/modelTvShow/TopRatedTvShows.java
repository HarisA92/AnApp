package com.themovie.anapp.retrofit.model.modelTvShow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TopRatedTvShows {

    @SerializedName("results")
    @Expose
    private List<TvShowResult> results = new ArrayList<>();

    public List<TvShowResult> getResults() {
        return results;
    }


}
