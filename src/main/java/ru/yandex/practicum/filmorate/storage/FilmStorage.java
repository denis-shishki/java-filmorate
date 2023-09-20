package ru.yandex.practicum.filmorate.storage;

import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.List;

public interface FilmStorage {
    public List<Film> getAllFilm();

    public Film createFilm(Film film) throws ValidationException;

    public Film updateFilm(Film film) throws ValidationException, NotFoundException;

    public Film getFilmById(int id);
}
