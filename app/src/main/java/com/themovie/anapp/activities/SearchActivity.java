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
import com.themovie.anapp.adapters.SearchMovieAdapter;
import com.themovie.anapp.adapters.SearchTvShowAdapter;
import com.themovie.anapp.adapters.TvShowAdapter;
import com.themovie.anapp.retrofit.ModelClient;
import com.themovie.anapp.retrofit.RetrofitClient;
import com.themovie.anapp.retrofit.model.modelMovie.Result;
import com.themovie.anapp.retrofit.model.modelMovie.TopRatedMovies;
import com.themovie.anapp.retrofit.model.modelTvShow.TopRatedTvShows;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {

    String getMovieTab, getTvShowTab;
    private List<Result> listMovies;
    private List<com.themovie.anapp.retrofit.model.modelTvShow.Result> listTvShow;
    private MovieAdapter movieAdapter;
    private TvShowAdapter tvShowAdapter;
    private SearchMovieAdapter searchMovieAdapter;
    private SearchTvShowAdapter searchTvShowAdapter;
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

    private void setUpSearchActivity() {
        if (getMovieTab != null && getTvShowTab == null) {
            setUpTop10Movies();
        } else {
            setUpTop10TvShows();
        }
    }

    private void setUpTop10TvShows() {
        compositeDisposable.add(client.getTvShows(BuildConfig.ApiKey, getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopRatedTvShows>() {
                    @Override
                    public void accept(TopRatedTvShows topRatedTvShows) {
                        listTvShow = topRatedTvShows.getResults();
                        List<com.themovie.anapp.retrofit.model.modelTvShow.Result> exampleList = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            com.themovie.anapp.retrofit.model.modelTvShow.Result result = listTvShow.get(i);
                            exampleList.add(result);
                        }
                        tvShowAdapter = new TvShowAdapter(getApplicationContext(), exampleList);
                        recyclerView.setAdapter(tvShowAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setUpTop10Movies() {
        compositeDisposable.add(client.getMovies(BuildConfig.ApiKey, getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopRatedMovies>() {
                    @Override
                    public void accept(TopRatedMovies topRatedMovies) {
                        listMovies = topRatedMovies.getResults();
                        List<Result> exampleListFull = new ArrayList<>();
                        for (int i = 0; i < 10; i++) {
                            Result result = listMovies.get(i);
                            exampleListFull.add(result);
                        }
                        movieAdapter = new MovieAdapter(getApplicationContext(), exampleListFull);
                        recyclerView.setAdapter(movieAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setUpSearchMovies(String getQuery) {
        compositeDisposable.add(client.getSearchedMovies(BuildConfig.ApiKey, getResources().getString(R.string.language), getQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopRatedMovies>() {
                    @Override
                    public void accept(TopRatedMovies topRatedMovies) {
                        listMovies = topRatedMovies.getResults();
                        searchMovieAdapter = new SearchMovieAdapter(getApplicationContext(), listMovies);
                        recyclerView.setAdapter(searchMovieAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setUpSearchTvShows(String getQuery) {
        compositeDisposable.add(client.getSearchedTvShows(BuildConfig.ApiKey, getResources().getString(R.string.language), getQuery)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<TopRatedTvShows>() {
                    @Override
                    public void accept(TopRatedTvShows topRatedTvShows) {
                        listTvShow = topRatedTvShows.getResults();
                        searchTvShowAdapter = new SearchTvShowAdapter(getApplicationContext(), listTvShow);
                        recyclerView.setAdapter(searchTvShowAdapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        Toast.makeText(SearchActivity.this, getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private void setUpRecyclerView() {
        recyclerView = findViewById(R.id.recycler_view_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
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
                if (getMovieTab != null && getTvShowTab == null) {
                    if (s.length() > 3) {
                        setUpSearchMovies(s);
                        try {
                            searchMovieAdapter.getFilter().filter(s);
                        } catch (Exception ignored) {
                        }
                    }
                } else if (getMovieTab == null && getTvShowTab != null) {
                    if (s.length() > 3) {
                        setUpSearchTvShows(s);
                        try {
                            searchTvShowAdapter.getFilter().filter(s);
                        } catch (Exception ignored) {
                        }
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
                if (getMovieTab != null && getTvShowTab == null) {
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
}
