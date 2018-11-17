package com.themovie.anapp.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.themovie.anapp.R;
import com.themovie.anapp.adapters.TvShowAdapter;
import com.themovie.anapp.retrofit.ModelClient;
import com.themovie.anapp.retrofit.RetrofitClient;
import com.themovie.anapp.retrofit.model.modelTvShow.Result;
import com.themovie.anapp.retrofit.model.modelTvShow.TopRatedTvShows;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class TvShowFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private List<Result> list;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tv_show, container, false);
        recyclerView = v.findViewById(R.id.recycler_view_tvshows);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        rxjava();
        return v;
    }

    private void rxjava() {
        RetrofitClient client = ModelClient.retrofitclient();
        Observable<TopRatedTvShows> observable = client.getTvShows(getResources().getString(R.string.category), "097bff8b86812605efe2030471a36a24", getResources().getString(R.string.language), 1)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        observable.subscribe(new Observer<TopRatedTvShows>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TopRatedTvShows topRatedTvShows) {
                list = topRatedTvShows.getResults();
                TvShowAdapter adapter = new TvShowAdapter(list, getActivity());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
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
