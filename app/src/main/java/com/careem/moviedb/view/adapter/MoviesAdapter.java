package com.careem.moviedb.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.careem.moviedb.BR;
import com.careem.moviedb.R;
import com.careem.moviedb.model.Movie;
import com.careem.moviedb.util.ImageUtil;
import com.careem.moviedb.view.viewholder.CustomViewHolder;

import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<CustomViewHolder> {

    private final ImageUtil imageUtil;
    private List<Movie> movies;
    private OnItemClickListener onItemClickListener;

    public MoviesAdapter(ImageUtil imageUtil) {
        this.imageUtil = imageUtil;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        ViewDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(viewGroup.getContext()),
                R.layout.movie_item, viewGroup, false);
        binding.setVariable(BR.imageUtil, imageUtil);
        return new CustomViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(CustomViewHolder customViewHolder, int i) {
        ViewDataBinding viewDataBinding = customViewHolder.getViewDataBinding();
        viewDataBinding.setVariable(BR.movie, movies.get(i));
        viewDataBinding.getRoot().setTag(i);
        viewDataBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = (int) view.getTag();
                if (onItemClickListener != null && movies.size() > position) {
                    onItemClickListener.onItemClick(movies.get(position));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return null != movies ? movies.size() : 0;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(Movie movie);
    }
}
