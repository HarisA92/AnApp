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
import com.themovie.anapp.R;
import com.themovie.anapp.activities.HolderActivity;
import com.themovie.anapp.retrofit.model.modelTvShow.TvShowResult;

import java.util.List;

public class TvShowAdapter extends RecyclerView.Adapter<TvShowAdapter.ViewHolder> {

    private List<TvShowResult> list;
    private Context context;

    public TvShowAdapter(Context context, List<TvShowResult> list) {
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
        final TvShowResult result = list.get(position);
        holder.title.setText(result.getName());
        Glide.with(context).load(context.getResources().getString(R.string.imagePath) + result.getPosterPath())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(), HolderActivity.class);
            intent.putExtra(context.getResources().getString(R.string.title), result.getName());
            intent.putExtra(context.getResources().getString(R.string.overview), result.getOverview());
            intent.putExtra(context.getResources().getString(R.string.backdropPath), result.getBackdropPath());
            view.getContext().startActivity(intent);
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
