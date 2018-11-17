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
import com.themovie.anapp.retrofit.model.modelMovie.Result;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder>{

    private Context context;
    private List<Result> list;

    public MovieAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(context).inflate(R.layout.movies_tvshows_adapter, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Result movieList = list.get(position);
        Glide.with(context).load(context.getResources().getString(R.string.imagePath) + movieList.getPosterPath())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.title.setText(movieList.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HolderActivity.class);
                intent.putExtra(context.getResources().getString(R.string.title), movieList.getTitle());
                intent.putExtra(context.getResources().getString(R.string.overview), movieList.getOverview());
                intent.putExtra(context.getResources().getString(R.string.backdropPath), movieList.getBackdropPath());
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
