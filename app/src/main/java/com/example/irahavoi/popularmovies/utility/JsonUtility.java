package com.example.irahavoi.popularmovies.utility;

import com.example.irahavoi.popularmovies.domain.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtility {
    public static List<Movie> getMoviesFromJson(String json){
        List<Movie> movies = new ArrayList<>();
        final String RESULTS = "results";

        //Movie Data:
        final String ADULT = "adult";
        final String GENRE_IDS = "genre_ids";
        final String ID = "id";
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
}
