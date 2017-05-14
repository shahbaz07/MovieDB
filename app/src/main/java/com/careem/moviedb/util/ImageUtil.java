package com.careem.moviedb.util;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class ImageUtil {

    private final Picasso picasso;

    public ImageUtil(Context context) {
        picasso = Picasso.with(context);
    }

    public void loadImage(final String url, final ImageView imageView) {
        picasso.load(url).into(imageView);
    }
}
