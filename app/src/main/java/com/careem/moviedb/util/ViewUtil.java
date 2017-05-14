package com.careem.moviedb.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.widget.Toast;

public class ViewUtil {

    private final Context context;
    private final Handler handler;

    public ViewUtil(Context context) {
        this.context = context;
        handler = new Handler(Looper.getMainLooper());
    }

    public void showToast(@StringRes int msg) {
        showToast(context.getString(msg));
    }

    public void showToast(final String msg) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
