package com.careem.moviedb.communication.listener;

import android.support.annotation.Nullable;

public interface ApiListener<T> {
    void onSuccess(@Nullable T object);

    void onError(Exception e);
}
