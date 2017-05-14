package com.careem.moviedb.communication.parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonParser {

    private Gson gson;

    public JsonParser() {
        gson = new GsonBuilder().create();
    }

    public Gson getGson() {
        return gson;
    }
}
