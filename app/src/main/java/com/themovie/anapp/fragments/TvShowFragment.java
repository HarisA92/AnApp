package com.themovie.anapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.themovie.anapp.BuildConfig;
import com.themovie.anapp.R;
import com.themovie.anapp.activities.SearchActivity;
import com.themovie.anapp.adapters.TvShowAdapter;
import com.themovie.anapp.retrofit.ModelClient;
import com.themovie.anapp.retrofit.RetrofitClient;
import com.themovie.anapp.retrofit.model.modelTvShow.TvShowResult;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowFragment extends Fragment {

    private List<TvShowResult> listTvShow;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitClient client = ModelClient.retrofitclient();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv_show, container, false);
        setHasOptionsMenu(true);
        buildRecyclerView(v);
        setUpTop10();
        return v;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_search: {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                intent.putExtra(getResources().getString(R.string.tvshow_holder), "TVSHOW_FRAGMENT");
                startActivity(intent);
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        recyclerView.setAdapter(null);
        recyclerView.setLayoutManager(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    private void setUpTop10() {
        compositeDisposable.add(client.getTvShows(BuildConfig.ApiKey, getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(topRatedTvShows -> {
                    listTvShow = topRatedTvShows.getResults();
                    TvShowAdapter adapter = new TvShowAdapter(getTop10TvShow(listTvShow));
                    recyclerView.setAdapter(adapter);
                }, throwable -> Toast.makeText(getActivity(), getResources().getString(R.string.error) + throwable.getMessage(), Toast.LENGTH_SHORT).show()));
    }

    private List<TvShowResult> getTop10TvShow(List<TvShowResult> tvShowResults) {
        List<TvShowResult> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            TvShowResult result = tvShowResults.get(i);
            list.add(result);
        }
        return list;
    }

    private void buildRecyclerView(View v) {
        recyclerView = v.findViewById(R.id.recycler_view_tvshows);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }

}
