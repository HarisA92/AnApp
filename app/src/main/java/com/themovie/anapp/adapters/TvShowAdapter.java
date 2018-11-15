package com.themovie.anapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.themovie.anapp.HolderActivity;
import com.themovie.anapp.R;
import com.themovie.anapp.retrofit.model.modelTvShow.Result;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder>{

    private List<Result> list;
    private Context context;

    public TvShowAdapter(List<Result> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.movies_tvshows_adapter, viewGroup, false);
        return new TvShowAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result result = list.get(position);
        holder.title.setText(result.getName());
        Glide.with(context).load("https://image.tmdb.org/t/p/w500/" + result.getPosterPath())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HolderActivity.class);
                intent.putExtra("position", result.getName());
                intent.putExtra("position2", result.getOverview());
                intent.putExtra("position3", result.getBackdropPath());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        ImageView image;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title_text);
            image = itemView.findViewById(R.id.image_movie);
        }
    }
}
