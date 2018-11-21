package com.themovie.anapp.retrofit.model.modelTvShow;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TvShowResult {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;
    @SerializedName("overview")
    @Expose
    private String overview;
    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    public String getName() {
        return name;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

}
