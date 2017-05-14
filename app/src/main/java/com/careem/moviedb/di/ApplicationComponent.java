package com.careem.moviedb.di;

import android.app.Application;
import android.content.Context;

import com.careem.moviedb.MovieApplication;
import com.careem.moviedb.communication.parser.JsonParser;
import com.careem.moviedb.util.ImageUtil;
import com.careem.moviedb.util.ViewUtil;

import dagger.Component;
import dagger.Module;
import dagger.Provides;

@PerApplication
@Component(modules = ApplicationComponent.ApplicationModule.class)

public interface ApplicationComponent {

    void inject(MovieApplication application);

    Context exposeContext();
    JsonParser exposeJsonParser();
    ImageUtil exposeImageUtil();
    ViewUtil exposeViewUtil();

    @Module
    class ApplicationModule {

        private final Application application;

        public ApplicationModule(Application app) {
            application = app;
        }

        @Provides
        @PerApplication
        Context providesContext() {
            return application;
        }

        @Provides
        @PerApplication
        JsonParser providesJsonParser() {
            return new JsonParser();
        }

        @Provides
        @PerApplication
        ImageUtil providesImageUtil(Context context) {
            return new ImageUtil(context);
        }

        @Provides
        @PerApplication
        ViewUtil providesViewUtil(Context context) {
            return new ViewUtil(context);
        }
    }
}