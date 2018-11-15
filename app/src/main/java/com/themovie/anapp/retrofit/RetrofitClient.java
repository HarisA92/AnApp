package com.themovie.anapp.retrofit;

import com.themovie.anapp.retrofit.model.modelMovie.TopRatedMovies;
import com.themovie.anapp.retrofit.model.modelTvShow.TopRatedTvShows;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitClient {

    @GET("/3/movie/{category}")
    Observable<TopRatedMovies> getMovies(@Path("category") String category,
                                         @Query("api_key") String api_key,
                                         @Query("q") String language,
                                         @Query("page") int page);

    @GET("/3/tv/{category}")
    Observable<TopRatedTvShows> getTvShows(@Path("category") String category,
                                           @Query("api_key") String api_key,
                                           @Query("q") String language,
                                           @Query("page") int page);

}
