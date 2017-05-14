package com.careem.moviedb.communication;

import android.content.Context;
import android.support.annotation.NonNull;

import com.careem.moviedb.R;
import com.careem.moviedb.common.Constants;
import com.careem.moviedb.common.Logger;
import com.careem.moviedb.communication.exception.ServerException;
import com.careem.moviedb.communication.listener.ApiListener;
import com.careem.moviedb.communication.parser.JsonParser;
import com.careem.moviedb.communication.respone.MoviesResponse;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MoviesDataSource {

    private static final String TAG = MoviesDataSource.class.getSimpleName();

    private MoviesAPI moviesAPI;
    private final String requestTimeoutError, requestFailError, responseError;

    public MoviesDataSource(Context context, JsonParser jsonParser) {
        init(jsonParser);
        requestTimeoutError = context.getString(R.string.error_request_timeout);
        requestFailError = context.getString(R.string.error_request_timeout);
        responseError = context.getString(R.string.something_went_wrong);
    }

    private void init(JsonParser jsonParser) {
        final OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder()
                .readTimeout(Constants.READ_TIMEOUT_SECONDS, TimeUnit.SECONDS)
                .connectTimeout(Constants.CONNECTION_TIMEOUT_SECONDS, TimeUnit.SECONDS);

        final GsonConverterFactory gsonConverterFactory = GsonConverterFactory.create(jsonParser.getGson());

        moviesAPI = new Retrofit.Builder()
                .client(okHttpClientBuilder.build())
                .addConverterFactory(gsonConverterFactory)
                .baseUrl(Constants.API_URL).build().create(MoviesAPI.class);
    }

    public void loadMovies(int page, ApiListener<MoviesResponse> listener) {
        moviesAPI.getMovies(Constants.API_KEY, Constants.DEFAULT_LANGUAGE, page)
                .enqueue(createCallback(listener));
    }

    public void loadMovies(int page, String startDate, String endDate, ApiListener<MoviesResponse> listener) {
        moviesAPI.getMovies(Constants.API_KEY, Constants.DEFAULT_LANGUAGE, page, startDate, endDate)
                .enqueue(createCallback(listener));
    }

    protected <T> Callback<T> createCallback(@NonNull final ApiListener<T> listener) {
        return new Callback<T>() {
            @Override
            public void onResponse(Call<T> call, Response<T> response) {
                if (response.isSuccessful()) {
                    T body = response.body();
                    listener.onSuccess(body);
                } else {
                    listener.onError(new ServerException(response.code(), responseError));
                }
            }

            @Override
            public void onFailure(Call<T> call, Throwable t) {
                if (!call.isCanceled()) {
                    Logger.w(TAG, t);
                    if (t instanceof IOException) {
                        listener.onError(new IOException(requestTimeoutError));
                    } else {
                        listener.onError(new Exception(requestFailError));
                    }
                }
            }
        };
    }
}
