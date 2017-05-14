package com.careem.moviedb.common;

import android.util.Log;

public class Logger {

    private static final String TAG = Logger.class.getSimpleName();

    public static void w(String msg) {
        w(TAG, msg);
    }

    public static void w(String tag, String msg) {
        Log.w(tag, msg);
    }

    public static void w(String tag, Throwable t) {
        Log.w(tag, t);
    }
}
