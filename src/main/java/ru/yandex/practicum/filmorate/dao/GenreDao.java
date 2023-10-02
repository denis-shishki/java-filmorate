package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    Genre findGenreById(int id);

    void createConnectionGenreWithFilm(int filmId, int genreId);

    void createConnectionGenreWithFilm(int filmId, List<Genre> genres);

    List<Genre> findAllGenres();

    List<Genre> findGenresByPostId(int id);

    boolean existsGenreById(int id);
}
