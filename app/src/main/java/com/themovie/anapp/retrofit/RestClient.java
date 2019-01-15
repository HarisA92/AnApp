package com.themovie.anapp.retrofit;

import com.themovie.anapp.BuildConfig;
import com.themovie.anapp.retrofit.model.modelMovie.TopRatedMovies;
import com.themovie.anapp.retrofit.model.modelTvShow.TopRatedTvShows;

import io.reactivex.Observable;

public class RestClient {

    private RetrofitClient retrofitClient;

    private final static String LANGUAGE = "en";

    public RestClient() {
        this.retrofitClient = ModelClient.retrofitclient();
    }

    public Observable<TopRatedMovies> getTopRatedMovies() {
        return retrofitClient.getMovies(BuildConfig.ApiKey, LANGUAGE, 1);
    }

    public Observable<TopRatedTvShows> getTopRatedTvShows() {
        return retrofitClient.getTvShows(BuildConfig.ApiKey, LANGUAGE, 1);
    }

    public Observable<TopRatedMovies> searchMovies(String query) {
        return retrofitClient.getSearchedMovies(BuildConfig.ApiKey, LANGUAGE, query);
    }

    public Observable<TopRatedTvShows> searchTvShows(String query) {
        return retrofitClient.getSearchedTvShows(BuildConfig.ApiKey, LANGUAGE, query);
    }

}
