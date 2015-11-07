package com.example.irahavoi.popularmovies;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.view.MovieDetailFragment;
import com.example.irahavoi.popularmovies.view.MovieFragment;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity implements MovieFragment.Callback, MovieDetailFragment.OnFragmentInteractionListener{
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        if (findViewById(R.id.movie_detail) != null) {
            mTwoPane = true;
            if(savedInstanceState == null){
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.movie_detail, new MovieDetailFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
            getSupportActionBar().setElevation(0f);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(Uri uri, Movie movie) {
        if(mTwoPane){
            Bundle args = new Bundle();
            args.putParcelable(MovieDetailFragment.DETAIL_URI, uri);
            args.putParcelable(MovieDetailFragment.SELECTED_MOVIE, movie);

            MovieDetailFragment fragment = new MovieDetailFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else{
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .setData(uri);

            intent.putExtra(MovieDetailFragment.SELECTED_MOVIE, (Serializable)movie);
            startActivity(intent);


        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        Toast.makeText(this, "TODO: figure out how to use the onFragmentInteraction method", Toast.LENGTH_LONG);
    }
}
