package com.example.irahavoi.popularmovies.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.irahavoi.popularmovies.R;
import com.example.irahavoi.popularmovies.domain.Trailer;

import java.util.List;

public class MovieTrailerAdapter extends ArrayAdapter<Trailer>{
    private Context context;
    private List<Trailer> trailers;
    private int layoutId;
    private LayoutInflater inflater;

    public MovieTrailerAdapter(Context context, int layoutId, List<Trailer> trailers) {
        super(context, layoutId, trailers);
        this.context = context;
        this.layoutId = layoutId;
        this.trailers = trailers;

        inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Trailer trailer = trailers.get(position);
        final View rootView = inflater.inflate(layoutId, parent, false);

        TextView nameView = (TextView)rootView.findViewById(R.id.trailerName);
        nameView.setText(getTrailers().get(position).getName());

        return rootView;
    }

    public List<Trailer> getTrailers() {
        return trailers;
    }

    public void setTrailers(List<Trailer> trailers) {
        this.trailers = trailers;
    }
}
