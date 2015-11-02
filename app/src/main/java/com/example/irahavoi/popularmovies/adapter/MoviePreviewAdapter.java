package com.example.irahavoi.popularmovies.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.utility.PicassoImageHelper;

import java.util.List;

/**
 * Adapter for Movie Previews.
 */
public class MoviePreviewAdapter extends ArrayAdapter<Movie>{
    private Context context;
    private View previewMovieLayout;
    private List<Movie> movies;
    private int layoutId;
    private LayoutInflater inflater;
    private Drawable mDefaultDrawable;

    public MoviePreviewAdapter(Context context, int layoutId, List<Movie> movies){
        super(context, layoutId, movies);
        this.context = context;
        this.movies = movies;
        this.layoutId = layoutId;

        mDefaultDrawable = context.getResources().getDrawable(R.drawable.movie);

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Movie movie = movies.get(position);
        final View rootView = inflater.inflate(layoutId, parent, false);

        //Picasso.with(rootView.getContext()).load
        ImageView movieThumbnail = (ImageView) rootView.findViewById(R.id.movie_thumbnail);


        PicassoImageHelper.getImageByPosterPath(movie.getPosterPath(), rootView.getContext(), movieThumbnail, mDefaultDrawable);


        return rootView;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
