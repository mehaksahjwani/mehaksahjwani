package com.example.sahjwani_mymoviewishlist;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditMovieFragment extends DialogFragment {

    private EditMovieDialogListener listener;
    private Movie movie;
    private int position;

    public interface EditMovieDialogListener {
        void updateMovie(Movie updatedMovie, int position);
    }

    public static EditMovieFragment newInstance(Movie movie, int position) {
        EditMovieFragment fragment = new EditMovieFragment();
        Bundle args = new Bundle();
        args.putString("title", movie.getMovieTitle());
        args.putString("director", movie.getDirectorName());
        args.putString("genre", movie.getMovieGenre());
        args.putInt("year", movie.getReleaseYear());
        args.putString("status", movie.getStatus());
        args.putInt("position", position);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditMovieDialogListener) {
            listener = (EditMovieDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditMovieDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        assert getArguments() != null;
        movie = new Movie(
                getArguments().getString("title"),
                getArguments().getString("director"),
                getArguments().getString("genre"),
                getArguments().getInt("year"),
                getArguments().getString("status")
        );
        position = getArguments().getInt("position");

        View view = LayoutInflater.from(getContext()).inflate(R.layout.edit_movie_fragment, null);
        EditText movieTitleInput = view.findViewById(R.id.edit_movie_title);
        EditText directorInput = view.findViewById(R.id.edit_director);
        EditText genreInput = view.findViewById(R.id.edit_genre);
        EditText yearInput = view.findViewById(R.id.edit_year);
        EditText statusInput = view.findViewById(R.id.edit_status);

        // Pre-fill existing data and enable easy editing
        movieTitleInput.setText(movie.getMovieTitle());
        movieTitleInput.setSelectAllOnFocus(true);

        directorInput.setText(movie.getDirectorName());
        directorInput.setSelectAllOnFocus(true);

        genreInput.setText(movie.getMovieGenre());
        genreInput.setSelectAllOnFocus(true);

        yearInput.setText(String.valueOf(movie.getReleaseYear()));
        yearInput.setSelectAllOnFocus(true);

        statusInput.setText(movie.getStatus());
        statusInput.setSelectAllOnFocus(true);

        // Automatically select text when field gains focus
        movieTitleInput.requestFocus();
        movieTitleInput.post(() -> movieTitleInput.setSelection(movieTitleInput.getText().length()));

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit Movie")
                .setPositiveButton("Save", (dialog, which) -> {
                    String title = movieTitleInput.getText().toString();
                    String director = directorInput.getText().toString();
                    String genre = genreInput.getText().toString();
                    int year;
                    try {
                        year = Integer.parseInt(yearInput.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        yearInput.setError("Please enter a valid year");
                        return;
                    }
                    String status = statusInput.getText().toString();
                    Movie updatedMovie = new Movie(title, director, genre, year, status);
                    listener.updateMovie(updatedMovie, position);
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
