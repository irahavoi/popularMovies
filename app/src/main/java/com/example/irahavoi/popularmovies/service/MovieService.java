package com.example.irahavoi.popularmovies.service;

import android.app.IntentService;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.example.irahavoi.popularmovies.BuildConfig;
import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.domain.Review;
import com.example.irahavoi.popularmovies.domain.Trailer;
import com.example.irahavoi.popularmovies.utility.JsonUtility;
import com.example.irahavoi.popularmovies.utility.MovieServiceOperation;
import com.example.irahavoi.popularmovies.view.MovieDetailFragment;
import com.example.irahavoi.popularmovies.view.MoviesFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.irahavoi.popularmovies.utility.Constants.SERVICE_OPERATION_NAME;

public class MovieService extends IntentService{
    private final String LOG_TAG = MovieService.class.getSimpleName();
    private final String BASE_MOVIE_DB_URI = "http://api.themoviedb.org/3/";
    private final String SEARCH_MOVIES_URI = BASE_MOVIE_DB_URI + "discover/movie?sort_by=popularity.desc";
    private final String MOVIE = "movie/";
    private final String VIDEOS = "/videos";
    private final String REVIEWS = "/reviews";
    private final String API_KEY_PARAM = "api_key";
    private final String SORT_BY_PARAM = "sort_by";

    public MovieService(){
        super("MovieService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        MovieServiceOperation operation = (MovieServiceOperation)intent.getSerializableExtra(SERVICE_OPERATION_NAME);

        switch (operation){
            case GET_MOVIES:
                getMovies(intent);
                break;
            case GET_MOVIE_TRAILERS:
                getMovieTrailers(intent);
                break;
            case GET_MOVIE_REVIEWS:
                getMovieReviews(intent);
                break;
            default:
                throw new IllegalArgumentException("Illegal operation: " + operation);
        }
    }

    private void getMovies(Intent intent){
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String moviesJsonStr = null;

        try{

            String orderByPreference = intent.getStringExtra(MoviesFragment.ORDER_BY_PREFERENCE_EXTRA);
            String sortBy = "popularity.desc";
            if(orderByPreference.equals(getString(R.string.pref_sort_by_rating))){
                sortBy = "vote_average.desc";
            }

            Uri uri = Uri.parse(SEARCH_MOVIES_URI).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .appendQueryParameter(SORT_BY_PARAM, sortBy)
                    .build();

            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            moviesJsonStr = buffer.toString();
            List<Movie> movies = JsonUtility.getMoviesFromJson(moviesJsonStr);

            Intent moviesResponseIntent = new Intent(MoviesFragment.RECEIVE_MOVIES);
            moviesResponseIntent.putExtra(MoviesFragment.MOVIES_EXTRA, (ArrayList)movies);
            LocalBroadcastManager.getInstance(this).sendBroadcast(moviesResponseIntent);



        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
        return;
    }

    public void getMovieTrailers(Intent intent){
        Long movieId = intent.getLongExtra(MovieDetailFragment.SELECTED_MOVIE_ID, -1L);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String trailersJsonStr = null;

        try{
            Uri uri = Uri.parse(BASE_MOVIE_DB_URI + MOVIE + movieId + VIDEOS).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            trailersJsonStr = buffer.toString();
            List<Trailer> trailers = JsonUtility.getTrailersFromJson(trailersJsonStr);

            Intent trailersResponseIntent = new Intent(MovieDetailFragment.RECEIVE_TRAILERS);
            trailersResponseIntent.putExtra(MovieDetailFragment.TRAILERS_EXTRA, (ArrayList) trailers);
            LocalBroadcastManager.getInstance(this).sendBroadcast(trailersResponseIntent);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }

    public void getMovieReviews(Intent intent){
        Long movieId = intent.getLongExtra(MovieDetailFragment.SELECTED_MOVIE_ID, -1L);
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        String reviewsJsonStr = null;

        try{
            Uri uri = Uri.parse(BASE_MOVIE_DB_URI + MOVIE + movieId + REVIEWS).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, BuildConfig.MOVIE_DB_API_KEY)
                    .build();

            URL url = new URL(uri.toString());

            // Create the request to OpenWeatherMap, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                // Nothing to do.
                return;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return;
            }
            reviewsJsonStr = buffer.toString();
            List<Review> reviews = JsonUtility.getReviewsFromJson(reviewsJsonStr);

            Intent reviewsResponseIntent = new Intent(MovieDetailFragment.RECEIVE_REVIEWS);
            reviewsResponseIntent.putExtra(MovieDetailFragment.REVIEWS_EXTRA, (ArrayList)reviews);
            LocalBroadcastManager.getInstance(this).sendBroadcast(reviewsResponseIntent);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }
    }
}
