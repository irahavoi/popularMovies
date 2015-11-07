package com.example.irahavoi.popularmovies.view;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.domain.Movie;
import com.example.irahavoi.popularmovies.utility.PicassoImageHelper;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MovieDetailFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MovieDetailFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MovieDetailFragment extends android.support.v4.app.Fragment {
    public static final String DETAIL_URI = "URI";
    public static final String SELECTED_MOVIE = "SELECTED_MOVIE";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MovieDetailFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MovieDetailFragment newInstance(String param1, String param2) {
        MovieDetailFragment fragment = new MovieDetailFragment();
        Bundle args = new Bundle();
        //args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

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
        View detailView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        if(this.getArguments() != null){
            detailView.setVisibility(View.VISIBLE);
            Movie movie = (Movie)this.getArguments().getParcelable(SELECTED_MOVIE);

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
        }

        // Inflate the layout for this fragment
        return detailView;
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

}
