package com.example.irahavoi.popularmovies.data;

import android.provider.BaseColumns;

public class MovieContract {
    public static final class MovieEntry implements BaseColumns{
        public static final String ORIGINAL_LANGUAGE = "original_language";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String OVERVIEW = "overview";
        public static final String RELEASE_DATE = "release_date";
        public static final String POSTER_PATH = "poster_path";
        public static final String POPULARITY = "popularity";
        public static final String TITLE = "title";
        public static final String VIDEO = "video";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String VOTE_COUNT =  "vote_count";
    }
}
