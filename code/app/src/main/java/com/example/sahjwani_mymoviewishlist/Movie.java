package com.example.sahjwani_mymoviewishlist;

public class Movie {
    private String movieTitle;
    private String directorName;
    private String movieGenre;
    private int releaseYear;
    private String status;

    public Movie(String movieTitle, String directorName, String movieGenre, int releaseYear, String status) {
        this.movieTitle = movieTitle;
        this.directorName = directorName;
        this.movieGenre = movieGenre;
        this.releaseYear = releaseYear;
        this.status = status;
    }

    // Getters
    public String getMovieTitle() {
        return movieTitle;
    }

    public String getDirectorName() {
        return directorName;
    }

    public String getMovieGenre() {
        return movieGenre;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getStatus() {
        return status;
    }

    // Setters
    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setDirectorName(String directorName) {
        this.directorName = directorName;
    }

    public void setMovieGenre(String movieGenre) {
        this.movieGenre = movieGenre;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
