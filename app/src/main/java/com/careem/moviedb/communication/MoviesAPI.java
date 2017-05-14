package com.careem.moviedb.communication;

import com.careem.moviedb.common.Constants;
import com.careem.moviedb.communication.respone.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesAPI {

    @GET(Constants.API_MOVIES)
    Call<MoviesResponse> getMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                   @Query("page") int page);
    @GET(Constants.API_MOVIES)
    Call<MoviesResponse> getMovies(@Query("api_key") String apiKey, @Query("language") String language,
                                   @Query("page") int page, @Query("primary_release_date.gte") String startDate,
                                   @Query("primary_release_date.lte") String endDate);
}
