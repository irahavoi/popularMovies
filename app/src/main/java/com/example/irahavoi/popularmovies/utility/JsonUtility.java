package com.example.irahavoi.popularmovies.utility;

import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.domain.Review;
import com.example.irahavoi.popularmovies.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.irahavoi.popularmovies.data.MovieContract.MovieEntry.*;

public class JsonUtility {
    private final static String RESULTS = "results";
    private final static String ID = "id";

    public static List<Movie> getMoviesFromJson(String json){
        List<Movie> movies = new ArrayList<>();

        try{
            JSONObject moviesJson = new JSONObject(json);
            JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);

            for(int i = 0; i < moviesArray.length(); i++){
                Movie movie = new Movie();
                JSONObject movieJson = moviesArray.getJSONObject(i);

                movie.setId(movieJson.getInt(ID));
                movie.setOriginalLanguage(movieJson.getString(ORIGINAL_LANGUAGE));
                movie.setOriginalTitle(movieJson.getString(ORIGINAL_TITLE));
                movie.setOverview(movieJson.getString(OVERVIEW));
                movie.setReleaseDate(movieJson.getString(RELEASE_DATE));
                movie.setPosterPath(movieJson.getString(POSTER_PATH));
                movie.setPopularity(movieJson.getDouble(POPULARITY));
                movie.setTitle(movieJson.getString(TITLE));
                movie.setVideo(movieJson.getBoolean(VIDEO));
                movie.setVoteAverage(movieJson.getDouble(VOTE_AVERAGE));
                movie.setVoteCount(movieJson.getInt(VOTE_COUNT));
                movies.add(movie);

            }

        } catch (JSONException e){
            //TODO;
        }

        return movies;

    }

    public static List<Trailer> getTrailersFromJson(String json){
        List<Trailer> trailers = new ArrayList<>();

        final String ISO_639_1 = "iso_639_1";
        final String KEY = "key";
        final String NAME = "name";
        final String SITE = "site";
        final String SIZE = "size";
        final String TYPE = "type";

        try{
            JSONObject trailersJson = new JSONObject(json);
            JSONArray trailersArray = trailersJson.getJSONArray(RESULTS);

            for(int i = 0; i < trailersArray.length(); i++){
                Trailer trailer = new Trailer();
                JSONObject trailerJson = trailersArray.getJSONObject(i);

                trailer.setId(trailerJson.getString(ID));
                trailer.setIso639_2(trailerJson.getString(ISO_639_1));
                trailer.setKey(trailerJson.getString(KEY));
                trailer.setName(trailerJson.getString(NAME));
                trailer.setSite(trailerJson.getString(SITE));
                trailer.setSize(trailerJson.getInt(SIZE));
                trailer.setType(trailerJson.getString(TYPE));

                trailers.add(trailer);

            }

        } catch(JSONException e){
            //TODO
        }

        return trailers;
    }

    public static List<Review> getReviewsFromJson(String json){
        List<Review> reviews = new ArrayList<>();
        //Review Data:
        final String AUTHOR = "author";
        final String CONTENT = "content";

        try{
            JSONObject reviewsJson = new JSONObject(json);
            JSONArray reviewsArray = reviewsJson.getJSONArray(RESULTS);

            for(int i = 0; i < reviewsArray.length(); i++){
                JSONObject reviewJson = reviewsArray.getJSONObject(i);
                Review review = new Review(
                        reviewJson.getString(ID),
                        reviewJson.getString(AUTHOR),
                        reviewJson.getString(CONTENT));

                reviews.add(review);
            }

        } catch(JSONException e){
            //TODO
        }

        return reviews;
    }
}
