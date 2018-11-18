package com.themovie.anapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.themovie.anapp.R;
import com.themovie.anapp.adapters.MovieAdapter;
import com.themovie.anapp.adapters.TvShowAdapter;
import com.themovie.anapp.retrofit.ModelClient;
import com.themovie.anapp.retrofit.RetrofitClient;
import com.themovie.anapp.retrofit.model.modelMovie.TopRatedMovies;
import com.themovie.anapp.retrofit.model.modelTvShow.Result;
import com.themovie.anapp.retrofit.model.modelTvShow.TopRatedTvShows;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class TvShowFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Result> list;
    private RecyclerView recyclerView;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private RetrofitClient client = ModelClient.retrofitclient();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv_show, container, false);
        buildRecyclerView(v);
        setUpTop10();
        return v;
    }

    private void buildRecyclerView(View v){
        recyclerView = v.findViewById(R.id.recycler_view_tvshows);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
    }
    private void setUpTop10() {
        compositeDisposable.add(client.getTvShows("097bff8b86812605efe2030471a36a24", getResources().getString(R.string.language), 1)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TopRatedTvShows>() {
                    @Override
                    public void accept(TopRatedTvShows topRatedTvShows) throws Exception {
                        list = topRatedTvShows.getResults();
                        List<Result> exampleListFull = new ArrayList<>();
                        for(int i = 0; i<10; i++){
                            Result result = list.get(i);
                            exampleListFull.add(result);
                        }
                        TvShowAdapter adapter = new TvShowAdapter(getActivity(), exampleListFull);
                        recyclerView.setAdapter(adapter);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Toast.makeText(getActivity(), "Error: " + throwable.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
