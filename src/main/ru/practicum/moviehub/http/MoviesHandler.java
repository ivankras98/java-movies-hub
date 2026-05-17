package ru.practicum.moviehub.http;

import com.sun.net.httpserver.HttpExchange;
import ru.practicum.moviehub.api.ErrorResponse;
import ru.practicum.moviehub.model.Movie;
import ru.practicum.moviehub.store.MoviesStore;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

public class MoviesHandler extends BaseHttpHandler {
    private final MoviesStore store;

    public MoviesHandler(MoviesStore store) {
        this.store = store;
    }

    @Override
    public void handle(HttpExchange ex) throws IOException {
        String method = ex.getRequestMethod();
        if (method.equalsIgnoreCase("GET")) {
            String json = gson.toJson(store.getAll());
            sendJson(ex, 200, json);
        } else if (method.equalsIgnoreCase("POST")) {
            handlePost(ex);
        }
    }

    private void handlePost(HttpExchange ex) throws IOException {
        InputStream is = ex.getRequestBody();
        String body = new String(is.readAllBytes(), StandardCharsets.UTF_8);

        Movie movie = gson.fromJson(body, Movie.class);

        if (movie == null || movie.getName() == null || movie.getName().isBlank()) {
            String error = gson.toJson(new ErrorResponse("Название фильма не может быть пустым"));
            sendJson(ex, 400, error);
            return;
        }

        Movie created = store.add(movie);
        sendJson(ex, 201, gson.toJson(created));
    }
}