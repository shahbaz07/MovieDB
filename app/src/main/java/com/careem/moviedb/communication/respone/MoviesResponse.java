package com.careem.moviedb.communication.respone;

import com.careem.moviedb.model.Movie;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MoviesResponse {

    @SerializedName("page")
    private int page;

    @SerializedName("results")
    private List<Movie> movies;

    public int getPage() {
        return page;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
