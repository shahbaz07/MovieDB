package com.careem.moviedb.viewmodel;

public interface ViewModelListener {

    void onCreate();
    void onResume();
    void onPause();
    void onDestroy();
}
