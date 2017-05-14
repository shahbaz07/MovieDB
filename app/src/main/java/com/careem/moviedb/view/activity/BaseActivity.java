package com.careem.moviedb.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.careem.moviedb.MovieApplication;
import com.careem.moviedb.di.ActivityComponent;
import com.careem.moviedb.di.ApplicationComponent;
import com.careem.moviedb.util.ViewUtil;

import javax.inject.Inject;

public abstract class BaseActivity extends AppCompatActivity {

    @Inject
    protected ViewUtil viewUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        finishInjection();
    }

    protected ApplicationComponent getApplicationComponent() {
        return ((MovieApplication) getApplication()).getApplicationComponent();
    }

    protected ActivityComponent.ActivityModule getActivityModule() {
        return new ActivityComponent.ActivityModule(this);
    }

    protected abstract void finishInjection();
}
