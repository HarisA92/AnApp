package com.themovie.anapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.themovie.anapp.R;
import com.themovie.anapp.activities.HolderActivity;
import com.themovie.anapp.retrofit.model.modelMovie.Result;

import java.util.ArrayList;
import java.util.List;

public class SearchMovieAdapter extends RecyclerView.Adapter<SearchMovieAdapter.ViewHolder> implements Filterable {

    private Context context;
    private List<Result> list;
    private List<Result> copyList;

    private Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<Result> filteredList = new ArrayList<>();
            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(copyList);
            } else {
                String filterPattern = constraint.toString();
                for (Result result : copyList) {
                    if (result.getTitle().contains(filterPattern)) {
                        filteredList.add(result);
                    }
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            list.clear();
            list.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };

    public SearchMovieAdapter(Context context, List<Result> list) {
        this.context = context;
        this.list = list;
        copyList = new ArrayList<>(list);
    }

    @NonNull
    @Override
    public SearchMovieAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movies_tvshows_adapter, viewGroup, false);
        return new SearchMovieAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchMovieAdapter.ViewHolder holder, int position) {
        final Result result = list.get(position);
        holder.title.setText(result.getTitle());
        Glide.with(context).load(context.getResources().getString(R.string.imagePath) + result.getPosterPath())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.image);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HolderActivity.class);
                intent.putExtra(context.getResources().getString(R.string.title), result.getTitle());
                intent.putExtra(context.getResources().getString(R.string.overview), result.getOverview());
                intent.putExtra(context.getResources().getString(R.string.backdropPath), result.getBackdropPath());
                view.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
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
