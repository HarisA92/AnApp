package com.themovie.anapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;
import android.widget.Toast;

import com.themovie.anapp.BuildConfig;
import com.themovie.anapp.R;
import com.themovie.anapp.adapters.MovieAdapter;
import com.themovie.anapp.adapters.TvShowAdapter;
import com.themovie.anapp.retrofit.ModelClient;
import com.themovie.anapp.retrofit.RetrofitClient;
import com.themovie.anapp.retrofit.model.modelMovie.MovieResult;
import com.themovie.anapp.retrofit.model.modelTvShow.TvShowResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    private String getMovieTab, getTvShowTab;
    private List<MovieResult> listMovies;
    private List<TvShowResult> listTvShow;
    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RecyclerView recyclerView;
    private RetrofitClient client = ModelClient.retrofitclient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        setUpRecyclerView();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        getMovieTab = getIntent().getStringExtra(getResources().getString(R.string.movie_holder));
        getTvShowTab = getIntent().getStringExtra(getResources().getString(R.string.tvshow_holder));
        setUpSearchActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchItem.expandActionView();
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if (isMoviesTabSelected()) {
                    if (s.length() >= 3) {
                        setUpSearchMovies(s);
                        return true;
                    }
                } else {
                    if (s.length() >= 3) {
                        setUpSearchTvShows(s);
                        return true;
                    }
                }
                return false;
            }
        });
        searchItem.setOnActionExpandListener(new MenuItem.OnActionExpandListener() {
            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                if (isMoviesTabSelected()) {
                    setUpTop10Movies();
                } else {
                    setUpTop10TvShows();
                }
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void setUpTop10TvShows() {
        compositeDisposable.add(client.getTvShows(BuildConfig.ApiKey, getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedTvShows -> {
                    listTvShow = topRatedTvShows.getResults();
                    tvShowAdapter = new TvShowAdapter(getApplicationContext(), getTop10TvShow(listTvShow));
                    recyclerView.setAdapter(tvShowAdapter);
                }, throwable -> Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private void setUpTop10Movies() {
        compositeDisposable.add(client.getMovies(BuildConfig.ApiKey, getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedMovies -> {
                    listMovies = topRatedMovies.getResults();
                    movieAdapter = new MovieAdapter(getApplicationContext(), getTop10Movies(listMovies));
                    recyclerView.setAdapter(movieAdapter);
                }, throwable -> Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private void setUpSearchMovies(String getQuery) {
        compositeDisposable.add(client.getSearchedMovies(BuildConfig.ApiKey, getResources().getString(R.string.language), getQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedMovies -> {
                    listMovies = topRatedMovies.getResults();
                    movieAdapter = new MovieAdapter(getApplicationContext(), listMovies);
                    recyclerView.setAdapter(movieAdapter);
                }, throwable -> Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private void setUpSearchTvShows(String getQuery) {
        compositeDisposable.add(client.getSearchedTvShows(BuildConfig.ApiKey, getResources().getString(R.string.language), getQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedTvShows -> {
                    listTvShow = topRatedTvShows.getResults();
                    tvShowAdapter = new TvShowAdapter(getApplicationContext(), listTvShow);
                    recyclerView.setAdapter(tvShowAdapter);
                }, throwable -> Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private List<MovieResult> getTop10Movies(List<MovieResult> movieResults) {
        List<MovieResult> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            MovieResult result = movieResults.get(i);
            list.add(result);
        }
        return list;
    }

    private List<TvShowResult> getTop10TvShow(List<TvShowResult> tvShowResults) {
        List<TvShowResult> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TvShowResult result = tvShowResults.get(i);
            list.add(result);
        }
        return list;
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    private boolean isMoviesTabSelected() {
        return getMovieTab != null && getTvShowTab == null;
    }

    private void setUpSearchActivity() {
        if (isMoviesTabSelected()) {
            setUpTop10Movies();
        } else {
            setUpTop10TvShows();
        }
    }

}
