package com.careem.moviedb.di;

import android.content.Context;

import com.careem.moviedb.communication.MoviesDataSource;
import com.careem.moviedb.communication.parser.JsonParser;
import com.careem.moviedb.view.activity.BaseActivity;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerActivity
@Component (dependencies = ApplicationComponent.class,
    modules = ActivityComponent.ActivityModule.class)

public interface ActivityComponent {

    @Module
    class ActivityModule {
        private final BaseActivity activity;

        public ActivityModule(BaseActivity activity) {
            this.activity = activity;
        }

        @Provides
        @PerActivity
        BaseActivity activity() {
            return this.activity;
        }

        @Provides
        @PerActivity
        MoviesDataSource providesMoviesDataSource(Context context, JsonParser jsonParser) {
            return new MoviesDataSource(context, jsonParser);
        }

    }
}
