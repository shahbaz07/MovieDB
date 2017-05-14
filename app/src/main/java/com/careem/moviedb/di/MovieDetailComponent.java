package com.careem.moviedb.di;

import com.careem.moviedb.view.activity.MovieDetailActivity;

import dagger.Component;
import dagger.Module;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {MovieDetailComponent.MoviesModule.class, ActivityComponent.ActivityModule.class})

public interface MovieDetailComponent extends ActivityComponent {

    void inject(MovieDetailActivity activity);

    @Module
    class MoviesModule {

    }
}
