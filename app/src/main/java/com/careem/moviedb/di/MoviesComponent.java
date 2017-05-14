package com.careem.moviedb.di;

import android.content.Context;

import com.careem.moviedb.communication.MoviesDataSource;
import com.careem.moviedb.util.ImageUtil;
import com.careem.moviedb.util.ViewUtil;
import com.careem.moviedb.view.activity.MoviesActivity;
import com.careem.moviedb.view.adapter.MoviesAdapter;
import com.careem.moviedb.viewmodel.MoviesViewModel;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component(dependencies = ApplicationComponent.class,
        modules = {MoviesComponent.MoviesModule.class, ActivityComponent.ActivityModule.class})

public interface MoviesComponent extends ActivityComponent {

    void inject(MoviesActivity activity);

    @Module
    class MoviesModule {

        @Provides
        @PerActivity
        MoviesAdapter providesMoviesAdapter(ImageUtil imageUtil) {
            return new MoviesAdapter(imageUtil);
        }

        @Provides
        @PerActivity
        MoviesViewModel providesMoviesViewModel(Context context, MoviesDataSource moviesDataSource,
                                                MoviesAdapter moviesAdapter, ViewUtil viewUtil) {
            return new MoviesViewModel(context, moviesDataSource, moviesAdapter, viewUtil);
        }
    }
}
