package com.example.sahjwani_mymoviewishlist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class MovieAdapter extends ArrayAdapter<Movie> {

    public MovieAdapter(Context context, ArrayList<Movie> movieList) {
        super(context, 0, movieList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_item, parent, false);
        }

        Movie currentMovie = getItem(position);

        TextView title = convertView.findViewById(R.id.movie_title);
        TextView director = convertView.findViewById(R.id.director_name);
        TextView genre = convertView.findViewById(R.id.movie_genre);
        TextView releaseYear = convertView.findViewById(R.id.release_year);
        TextView status = convertView.findViewById(R.id.movie_status);

        title.setText(currentMovie.getMovieTitle());
        director.setText("Director: " + currentMovie.getDirectorName());
        genre.setText("Genre: " + currentMovie.getMovieGenre());
        releaseYear.setText("Release Year: " + currentMovie.getReleaseYear());
        status.setText("Status: " + currentMovie.getStatus());

        return convertView;
    }
}