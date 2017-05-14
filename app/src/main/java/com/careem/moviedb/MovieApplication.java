package com.careem.moviedb;

import android.app.Application;

import com.careem.moviedb.di.ApplicationComponent;
import com.careem.moviedb.di
        .DaggerApplicationComponent;

public class MovieApplication extends Application {

    private ApplicationComponent applicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationComponent.ApplicationModule(this))
                .build();
        applicationComponent.inject(this);
    }

    public ApplicationComponent getApplicationComponent() {
        return applicationComponent;
    }
}
