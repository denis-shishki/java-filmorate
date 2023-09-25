package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmDao {
    public List<Film> getAllFilm();

    public Film createFilm(Film film) throws ValidationException;

    public Film updateFilm(Film film) throws ValidationException, NotFoundException;

    void deleteAllGenreByFilmId(int id);

    void addLike(int filmId, int userId);

    int getNumberOfLikes(int filmId);

    List<Film> getSortFilmIdsByLikes(int count);

    void deleteLike(int filmId, int userId);

    public Film getFilmById(int id);

    boolean existsFilmById(int filmId);
}
