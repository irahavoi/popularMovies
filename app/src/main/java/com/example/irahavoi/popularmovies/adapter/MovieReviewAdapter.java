package com.example.irahavoi.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.domain.Review;

import java.util.List;


public class MovieReviewAdapter extends ArrayAdapter<Review> {
    private Context context;
    private List<Review> reviews;
    private int layoutId;
    private LayoutInflater inflater;

    public MovieReviewAdapter(Context context, int layoutId, List<Review> reviews) {
        super(context, layoutId, reviews);
        this.context = context;
        this.layoutId = layoutId;
        this.reviews = reviews;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Review review = reviews.get(position);
        final View rootView = inflater.inflate(layoutId, parent, false);

        TextView authorView = (TextView)rootView.findViewById(R.id.reviewAuthor);
        authorView.setText(getReviews().get(position).getAuthor());

        TextView contentView = (TextView)rootView.findViewById(R.id.reviewContent);
        contentView.setText(getReviews().get(position).getContent());

        return rootView;
    }


    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
