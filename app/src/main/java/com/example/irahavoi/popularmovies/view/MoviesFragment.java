package com.example.irahavoi.popularmovies.view;

import android.app.Activity;
import android.app.Fragment;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.SettingsActivity;
import com.example.irahavoi.popularmovies.adapter.MoviePreviewAdapter;
import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.service.MovieService;
import com.example.irahavoi.popularmovies.utility.MovieServiceOperation;
import com.example.irahavoi.popularmovies.utility.Utility;

import java.util.List;

import static com.example.irahavoi.popularmovies.utility.Constants.SERVICE_OPERATION_NAME;

public class MoviesFragment extends Fragment {

    public static final String RECEIVE_MOVIES = "com.example.irahavoi.popularmovies.ReceiveMovies";
    public static final String MOVIE_EXTRA = "movieExtra";
    public static final String MOVIES_EXTRA = "moviesExtra";
    public static final String ORDER_BY_PREFERENCE_EXTRA = "orderByPreference";

    private MoviePreviewAdapter mMoviePreviewAdapter;
    private String mOrderByPref;
    private Activity mActivity;
    private View mView;

    private BroadcastReceiver movieBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if(intent.getAction().equals(RECEIVE_MOVIES)){
                MoviesFragment.this.handleBroadcast(intent);
            }
        }
    };

    @Override
    public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        registerMoviesBroadCastReceiver();
        startMovieService();
        mActivity = getActivity();
        mView = inflater.inflate(R.layout.movie_previews_fragment, container, false);
        return mView;

    }

    private void handleBroadcast(Intent intent){
        List<Movie> movies = (List<Movie>)intent.getSerializableExtra(MOVIES_EXTRA);
        renderMoviePreviews(movies);

    }

    private void renderMoviePreviews(List<Movie>  movies){
        GridView gridView = (GridView)mView;
        mMoviePreviewAdapter = new MoviePreviewAdapter(mActivity, R.layout.movie_preview, movies);
        gridView.setAdapter(mMoviePreviewAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = mMoviePreviewAdapter.getMovies().get(position);
                ((Callback) getActivity()).onItemSelected(null, movie);
            }
        });
    }

    private void startMovieService(){
        mOrderByPref = Utility.getOrderByPreference(getActivity());
        Intent intent = new Intent(getActivity(), MovieService.class);
        intent.putExtra(SERVICE_OPERATION_NAME, MovieServiceOperation.GET_MOVIES);
        intent.putExtra(ORDER_BY_PREFERENCE_EXTRA, mOrderByPref);
        getActivity().startService(intent);
    }

    private void registerMoviesBroadCastReceiver(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_MOVIES);
        localBroadcastManager.registerReceiver(movieBroadCastReceiver, intentFilter);
    }

    @Override
    public void onResume(){
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String orderByPref = prefs.getString(getString(R.string.pref_sort_by_key), getString(R.string.pref_sort_by_popularity));

        if(!orderByPref.equals(mOrderByPref)){
            startMovieService();
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getActivity(), SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public interface Callback{
        public void onItemSelected(Uri uri, Movie mOvie);
    }

}
