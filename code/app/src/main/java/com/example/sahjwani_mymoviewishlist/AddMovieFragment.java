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

public class AddMovieFragment extends DialogFragment {

    private AddMovieDialogListener listener;

    public interface AddMovieDialogListener {
        void addMovie(Movie movie);
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof AddMovieDialogListener) {
            listener = (AddMovieDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement AddMovieDialogListener");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.add_movie_fragment, null);
        EditText movieTitleInput = view.findViewById(R.id.edit_movie_title);
        EditText directorInput = view.findViewById(R.id.edit_director);
        EditText genreInput = view.findViewById(R.id.edit_genre);
        EditText yearInput = view.findViewById(R.id.edit_year);
        EditText statusInput = view.findViewById(R.id.edit_status);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add a Movie")
                .setPositiveButton("Add", (dialog, which) -> {
                    String title = movieTitleInput.getText().toString();
                    String director = directorInput.getText().toString();
                    String genre = genreInput.getText().toString();
                    int year = 0;
                    try {
                        year = Integer.parseInt(yearInput.getText().toString().trim());
                    } catch (NumberFormatException e) {
                        yearInput.setError("Please enter a valid year");
                        return;
                    }
                    String status = statusInput.getText().toString();
                    Movie newMovie = new Movie(title, director, genre, year, status);
                    listener.addMovie(newMovie);
                })
                .setNegativeButton("Cancel", null)
                .create();
    }
}
