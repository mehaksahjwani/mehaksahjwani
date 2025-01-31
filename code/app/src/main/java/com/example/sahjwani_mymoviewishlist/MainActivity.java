package com.example.sahjwani_mymoviewishlist;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements AddMovieFragment.AddMovieDialogListener, EditMovieFragment.EditMovieDialogListener {

    private ListView listView;
    private ArrayList<Movie> movieList;
    private MovieAdapter movieAdapter;
    private TextView textTotalMovies, textWatchedMovies;

    @Override
    public void addMovie(Movie movie) {
        Log.d("MainActivity", "Add new movie");
        movieList.add(movie);
        movieAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Movie added", Toast.LENGTH_SHORT).show();
        updateMovieCounts();
    }

    @Override
    public void updateMovie(Movie updatedMovie, int position) {
        Log.d("MainActivity", "Movie edited");
        movieList.set(position, updatedMovie);
        movieAdapter.notifyDataSetChanged();
        Toast.makeText(this, "Movie updated", Toast.LENGTH_SHORT).show();
        updateMovieCounts();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.list_view);
        textTotalMovies = findViewById(R.id.text_total_movies);
        textWatchedMovies = findViewById(R.id.text_watched_movies);

        movieList = new ArrayList<>();

        movieAdapter = new MovieAdapter(this, movieList);
        listView.setAdapter(movieAdapter);

        FloatingActionButton fab = findViewById(R.id.button_add_movie);
        fab.setOnClickListener(v -> new AddMovieFragment().show(getSupportFragmentManager(), "Add Movie"));

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Movie clickedMovie = movieList.get(position);
            showEditDeleteDialog(clickedMovie, position);
        });

        updateMovieCounts();
    }

    private void showEditDeleteDialog(Movie movie, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        StringBuilder details = new StringBuilder();
        details.append("Title: ").append(movie.getMovieTitle()).append("\n");
        details.append("Director: ").append(movie.getDirectorName()).append("\n");
        details.append("Genre: ").append(movie.getMovieGenre()).append("\n");
        details.append("Year: ").append(movie.getReleaseYear()).append("\n");
        details.append("Status: ").append(movie.getStatus());

        builder.setTitle("Movie Details")
                .setMessage(details.toString())
                .setPositiveButton("Edit", (dialog, which) -> {
                    EditMovieFragment editMovieFragment = EditMovieFragment.newInstance(movie, position);
                    editMovieFragment.show(getSupportFragmentManager(), "Edit Movie");
                })
                .setNegativeButton("Delete", (dialog, which) -> {
                    movieList.remove(position);
                    movieAdapter.notifyDataSetChanged();
                    updateMovieCounts();
                    Toast.makeText(MainActivity.this, "Movie deleted", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Toggle Watched Status", (dialog, which) -> {
                    if ("Watched".equalsIgnoreCase(movie.getStatus())) {
                        movie.setStatus("Not Watched");
                    } else {
                        movie.setStatus("Watched");
                    }
                    movieAdapter.notifyDataSetChanged();
                    updateMovieCounts();
                    Toast.makeText(this, "Movie status updated", Toast.LENGTH_SHORT).show();
                })
                .setNeutralButton("Cancel", null)
                .show();
    }

    private void updateMovieCounts() {
        int totalMovies = movieList.size();
        int watchedMovies = (int) movieList.stream()
                .filter(movie -> movie.getStatus() != null && movie.getStatus().equalsIgnoreCase("Watched"))
                .count();

        textTotalMovies.setText("Total Movies: " + totalMovies);
        textWatchedMovies.setText("Watched Movies: " + watchedMovies);
    }
}
