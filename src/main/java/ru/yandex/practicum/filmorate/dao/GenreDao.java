package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Genre;

import java.util.Collection;
import java.util.List;

public interface GenreDao {
    public Genre findGenreById(int id);

    void createConnectionGenreWithFilm(int filmId, int genreId);

    public List<Genre> findAllGenres();

    List<Genre> findGenresByPostId(int id);

    boolean existsGenreById(int id);
}
