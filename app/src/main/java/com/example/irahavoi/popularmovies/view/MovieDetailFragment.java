package com.example.irahavoi.popularmovies.view;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.adapter.MovieReviewAdapter;
import com.example.irahavoi.popularmovies.adapter.MovieTrailerAdapter;
import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.domain.Review;
import com.example.irahavoi.popularmovies.domain.Trailer;
import com.example.irahavoi.popularmovies.service.MovieService;
import com.example.irahavoi.popularmovies.utility.MovieServiceOperation;
import com.example.irahavoi.popularmovies.utility.PicassoImageHelper;

import java.util.List;

import static com.example.irahavoi.popularmovies.utility.Constants.SERVICE_OPERATION_NAME;

public class MovieDetailFragment extends android.support.v4.app.Fragment {
    public static final String DETAIL_URI = "URI";
    public static final String SELECTED_MOVIE = "selectedMovie";

    public static final String RECEIVE_TRAILERS = "com.example.irahavoi.popularmovies.ReceiveTrailers";
    public static final String RECEIVE_REVIEWS = "com.example.irahavoi.popularmovies.ReceiveReviews";
    public static final String RECEIVE_ADD_TO_FAVORITES_CONFIRMATION = "com.example.irahavoi.popularmovies.AddtoFavoritesConfirmation";
    public static final String TRAILERS_EXTRA = "trailersExtra";
    public static final String REVIEWS_EXTRA = "reviewsExtra";

    private Movie movie;
    private Activity mActivity;
    private View mDetailView;
    private MovieReviewAdapter mMovieReviewAdapter;
    private MovieTrailerAdapter mMovieTrailerAdapter;

    private OnFragmentInteractionListener mListener;

    private BroadcastReceiver movieMetadataBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            MovieDetailFragment.this.handleBroadcast(intent);

        }
    };


    public MovieDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mDetailView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if(this.getArguments() != null){
            mDetailView.setVisibility(View.VISIBLE);
            movie = (Movie)this.getArguments().getSerializable(SELECTED_MOVIE);
            renderMovieDetails(movie, mDetailView);

            registerMetadataBroadCastReceiver();
            startMovieMetadataServices(movie);


        }

        // Inflate the layout for this fragment
        return mDetailView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    private void renderMovieDetails(final Movie movie, View detailView){
        ImageView thumbnailView = (ImageView)detailView.findViewById(R.id.movie_thumbnail);
        TextView titleView = (TextView)detailView.findViewById(R.id.originalTitle);
        TextView overviewView = (TextView)detailView.findViewById(R.id.overview);
        TextView ratingView = (TextView)detailView.findViewById(R.id.rating);
        TextView releaseDateView = (TextView)detailView.findViewById(R.id.releaseDate);

        Drawable defaultImage = getResources().getDrawable(R.drawable.movie);
        PicassoImageHelper.getImageByPosterPath(movie.getPosterPath(), getActivity(), thumbnailView, defaultImage);
        movie.getPosterPath();

        titleView.setText(movie.getOriginalTitle());
        overviewView.setText(movie.getOverview());

        String rating = movie.getVoteAverage() != null ? movie.getVoteAverage().toString() : "";
        ratingView.setText(rating);
        String date = movie.getReleaseDate() != null ? movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf("-")) : "";
        releaseDateView.setText(date);

        Button addToFavoritesBtn = (Button) detailView.findViewById(R.id.addToFavoritesButton);

        if(movie.isFavorite()){
            addToFavoritesBtn.setVisibility(View.INVISIBLE);
        } else{
            addToFavoritesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mActivity, MovieService.class);
                    Movie m = movie;
                    intent.putExtra(SELECTED_MOVIE, m);
                    intent.putExtra(SERVICE_OPERATION_NAME, MovieServiceOperation.ADD_MOVIE_TO_FAVORITES);
                    getActivity().startService(intent);
                    Toast.makeText(mActivity, "Added " + m.getOriginalTitle() + " to favorites!", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public void registerMetadataBroadCastReceiver(){
        LocalBroadcastManager localBroadcastManager = LocalBroadcastManager.getInstance(getActivity());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(RECEIVE_TRAILERS);
        intentFilter.addAction(RECEIVE_REVIEWS);
        intentFilter.addAction(RECEIVE_ADD_TO_FAVORITES_CONFIRMATION);
        localBroadcastManager.registerReceiver(movieMetadataBroadCastReceiver, intentFilter);
    }

    public void startMovieMetadataServices(Movie movie){
        Intent trailersIntent = new Intent(mActivity, MovieService.class);
        Intent reviewsIntent = new Intent(mActivity, MovieService.class);

        trailersIntent.putExtra(SELECTED_MOVIE, movie.getId());
        reviewsIntent.putExtra(SELECTED_MOVIE, movie.getId());
        trailersIntent.putExtra(SERVICE_OPERATION_NAME, MovieServiceOperation.GET_MOVIE_TRAILERS);
        reviewsIntent.putExtra(SERVICE_OPERATION_NAME, MovieServiceOperation.GET_MOVIE_REVIEWS);
        mActivity.startService(trailersIntent);
        mActivity.startService(reviewsIntent);
    }

    private void handleBroadcast(Intent intent){
        String action = intent.getAction();
        if(RECEIVE_REVIEWS.equals(action)){
            handleReviewsBroadcast(intent);
        } else if(RECEIVE_TRAILERS.equals(action)){
            handleTrailersBroadcast(intent);
        } else if(RECEIVE_ADD_TO_FAVORITES_CONFIRMATION.equals(action)){
            Button addTofavoritesBtn = (Button)mDetailView.findViewById(R.id.addToFavoritesButton);
            addTofavoritesBtn.setVisibility(View.INVISIBLE);
        }

    }

    private void handleReviewsBroadcast(Intent intent){
        List<Review> reviews  = (List<Review>)intent.getSerializableExtra(REVIEWS_EXTRA);
        mMovieReviewAdapter = new MovieReviewAdapter(mActivity, R.layout.movie_review, reviews);
        ListView reviewList = (ListView)mDetailView.findViewById(R.id.reviewList);
        reviewList.setAdapter(mMovieReviewAdapter);
    }

    private void handleTrailersBroadcast(Intent intent){
        List<Trailer> trailers  = (List<Trailer>)intent.getSerializableExtra(TRAILERS_EXTRA);
        mMovieTrailerAdapter = new MovieTrailerAdapter(mActivity, R.layout.movie_trailer, trailers);
        ListView trailerList = (ListView)mDetailView.findViewById(R.id.trailerList);
        trailerList.setAdapter(mMovieTrailerAdapter);

        trailerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                startYoutubeActivity(mMovieTrailerAdapter.getTrailers().get(position).getKey());
            }
        });
    }

    private void startYoutubeActivity(String videoId){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.youtube.com/watch?v=" + videoId)));
    }

}
