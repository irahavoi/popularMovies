package com.example.irahavoi.popularmovies;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.utility.PicassoImageHelper;
import com.example.irahavoi.popularmovies.view.MovieFragment;

public class MovieDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        Movie movie = (Movie)intent.getSerializableExtra(MovieFragment.MOVIE_EXTRA);

        setContentView(R.layout.activity_movie_detail);

        ImageView thumbnailView = (ImageView) findViewById(R.id.movie_thumbnail);
        TextView originalTitleView = (TextView) findViewById(R.id.originalTitle);
        TextView overviewView = (TextView) findViewById((R.id.overview));
        TextView ratingView = (TextView) findViewById(R.id.rating);
        TextView releaseDateView = (TextView)findViewById(R.id.releaseDate);

        originalTitleView.setText(movie.getOriginalTitle());
        overviewView.setText(movie.getOverview());
        ratingView.setText(movie.getVoteAverage().toString());
        releaseDateView.setText(movie.getReleaseDate());

        Drawable defaultImage = getResources().getDrawable(R.drawable.movie);
        PicassoImageHelper.getImageByPosterPath(movie.getPosterPath(), this, thumbnailView, defaultImage);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movie_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
