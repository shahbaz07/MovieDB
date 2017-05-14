package com.careem.moviedb.view.activity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.careem.moviedb.R;
import com.careem.moviedb.databinding.ActivityMovieDetailBinding;
import com.careem.moviedb.di.DaggerMovieDetailComponent;
import com.careem.moviedb.model.Movie;
import com.careem.moviedb.util.ImageUtil;

import javax.inject.Inject;

public class MovieDetailActivity extends BaseActivity {

    public static final String EXTRA_MOVIE = "movie";
    private ActivityMovieDetailBinding binding;

    @Inject
    protected ImageUtil imageUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie_detail);
        initViews();
    }

    private void initViews() {
        Movie movie = (Movie) getIntent().getExtras().getSerializable(EXTRA_MOVIE);
        binding.setImageUtil(imageUtil);
        binding.setMovie(movie);
    }

    @Override
    protected void finishInjection() {
        DaggerMovieDetailComponent.builder().applicationComponent(getApplicationComponent())
            .activityModule(getActivityModule()).build().inject(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
}
