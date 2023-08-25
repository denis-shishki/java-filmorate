package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public List<Film> findAllFilms();

    public Film createFilm(Film film) throws ValidationException;

    public Film updateFilm(Film film) throws ValidationException, FilmNotFoundException;

    public Film getFilmById(int id);
}
