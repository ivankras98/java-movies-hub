package ru.practicum.moviehub.store;

import ru.practicum.moviehub.model.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesStore {
    private final List<Movie> movies = new ArrayList<>();
    private int nextId = 1;

    public List<Movie> getAll() {
        return new ArrayList<>(movies);
    }

    public Movie add(Movie movie) {
        movie.setId(nextId++);
        movies.add(movie);
        return movie;
    }

    public void clear() {
        movies.clear();
        nextId = 1;
    }
}