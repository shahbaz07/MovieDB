package com.careem.moviedb.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BindingAdapter;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.widget.ImageView;

import com.careem.moviedb.common.Constants;
import com.careem.moviedb.communication.MoviesDataSource;
import com.careem.moviedb.communication.listener.ApiListener;
import com.careem.moviedb.communication.respone.MoviesResponse;
import com.careem.moviedb.model.Movie;
import com.careem.moviedb.util.ImageUtil;
import com.careem.moviedb.util.ViewUtil;
import com.careem.moviedb.view.activity.MovieDetailActivity;
import com.careem.moviedb.view.adapter.MoviesAdapter;
import com.careem.moviedb.view.listener.EndlessRecyclerViewScrollListener;

import java.util.ArrayList;
import java.util.List;

public class MoviesViewModel implements ViewModelListener, MoviesAdapter.OnItemClickListener {

    private final Context context;
    private final MoviesDataSource moviesDataSource;
    private final MoviesAdapter moviesAdapter;
    private final ViewUtil viewUtil;
    private int page = 1;
    private List<Movie> movies;
    private EndlessRecyclerViewScrollListener endlessScrollListener;
    private String startDate, endDate;

    private ApiListener<MoviesResponse> moviesListener;
    private ApiListener<MoviesResponse> moreMoviesListener;

    public MoviesViewModel(Context context, MoviesDataSource moviesDataSource,
                           MoviesAdapter moviesAdapter, ViewUtil viewUtil) {
        this.context = context;
        this.moviesDataSource = moviesDataSource;
        this.moviesAdapter = moviesAdapter;
        this.viewUtil = viewUtil;
        this.movies = new ArrayList<>();
    }

    @Override
    public void onCreate() {
        moviesListener = new ApiListener<MoviesResponse>() {
            @Override
            public void onSuccess(@Nullable MoviesResponse response) {
                endlessScrollListener.resetState();
                movies.clear();
                movies.addAll(response.getMovies());
                page = response.getPage();
                moviesAdapter.setMovies(movies);
            }

            @Override
            public void onError(Exception e) {
                viewUtil.showToast(e.getMessage());
            }
        };
        moreMoviesListener = new ApiListener<MoviesResponse>() {
            @Override
            public void onSuccess(@Nullable MoviesResponse response) {
                if (response.getMovies() != null && response.getMovies().size() > 0) {
                    endlessScrollListener.setHasMoreData(true);
                    movies.addAll(response.getMovies());
                    page = response.getPage();
                    moviesAdapter.setMovies(movies);
                } else {
                    endlessScrollListener.setHasMoreData(false);
                }
            }

            @Override
            public void onError(Exception e) {
                viewUtil.showToast(e.getMessage());
            }
        };
        moviesAdapter.setOnItemClickListener(this);
    }

    private void init() {
        page = 1;
        startDate = "";
        endDate = "";
        moviesDataSource.loadMovies(page, moviesListener);
    }

    @Override
    public void onResume() {
        init();
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onDestroy() {
    }

    public EndlessRecyclerViewScrollListener getScrollListener(LinearLayoutManager layoutManager) {
        endlessScrollListener = new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                loadMoreMovies();
            }
        };
        return endlessScrollListener;
    }

    public void loadMoreMovies() {
        if (TextUtils.isEmpty(startDate) && TextUtils.isEmpty(endDate)) {
            moviesDataSource.loadMovies(++page, moreMoviesListener);
        } else {
            moviesDataSource.loadMovies(++page, startDate, endDate, moreMoviesListener);
        }
    }

    public void loadMoviesByDate(String startDate, String endDate) {
        page = 1;
        this.startDate = startDate;
        this.endDate = endDate;
        moviesDataSource.loadMovies(page, startDate, endDate, moviesListener);
    }

    @BindingAdapter({"image_url", "image_util"})
    public static void setImage(ImageView view, String url, ImageUtil imageUtil) {
        imageUtil.loadImage(Constants.IMAGE_BASE_URL + url, view);
    }

    @Override
    public void onItemClick(Movie movie) {
        Intent intent = new Intent(context, MovieDetailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
        context.startActivity(intent);
    }
}