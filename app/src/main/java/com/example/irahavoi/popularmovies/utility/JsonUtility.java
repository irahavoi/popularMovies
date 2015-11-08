package com.example.irahavoi.popularmovies.utility;

import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.domain.Review;
import com.example.irahavoi.popularmovies.domain.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtility {
    private final static String RESULTS = "results";
    private final static String ID = "id";

    public static List<Movie> getMoviesFromJson(String json){
        List<Movie> movies = new ArrayList<>();

        //Movie Data:
        final String ADULT = "adult";
        final String GENRE_IDS = "genre_ids";
        final String ORIGINAL_LANGUAGE = "original_language";
        final String ORIGINAL_TITLE = "original_title";
        final String OVERVIEW = "overview";
        final String RELEASE_DATE = "release_date";
        final String POSTER_PATH = "poster_path";
        final String POPULARITY = "popularity";
        final String TITLE = "title";
        final String VIDEO = "video";
        final String VOTE_AVERAGE = "vote_average";
        final String VOTE_COUNT =  "vote_count";


        try{
            JSONObject moviesJson = new JSONObject(json);
            JSONArray moviesArray = moviesJson.getJSONArray(RESULTS);

            for(int i = 0; i < moviesArray.length(); i++){
                Movie movie = new Movie();
                JSONObject movieJson = moviesArray.getJSONObject(i);

                movie.setAdult(movieJson.getBoolean(ADULT));
                movie.setId(movieJson.getLong(ID));
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


                JSONArray genreIdsJson = movieJson.getJSONArray(GENRE_IDS);
                List<Long> genreIds = new ArrayList<>();

                for(int j = 0; j < genreIdsJson.length(); j++){
                    genreIds.add(genreIdsJson.getLong(j));
                }

                movie.setGenreIds(genreIds);

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
