package com.example.irahavoi.popularmovies.utility;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoImageHelper {
    private static String BASE_IMAGE_URL = "http://image.tmdb.org/t/p/w500";

    public static void getImageByPosterPath(String posterPath, Context context, ImageView imageView, Drawable defaultImage){
        Picasso.with(context).load(BASE_IMAGE_URL + posterPath).error(defaultImage).noFade().into(imageView);
    }
}
