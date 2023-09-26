package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;

@Service
public class FilmService {

    private final FilmDao filmStorage;
    private final UserDao userStorage;

    @Autowired
    public FilmService(FilmDao filmStorage, UserDao userStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
    }

    public void likeFilm(int filmId, int userId) throws NotFoundException {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, filmId);
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);

        filmStorage.addLike(filmId, userId);
    }

    public Film getFilmById(int id) throws NotFoundException {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, id);
        return filmStorage.getFilmById(id);
    }

    public void deleteLike(int filmId, int userId) throws Exception {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, filmId);
        UserValidator.checkCorrectVariableIdUser(userStorage, userId);

        filmStorage.deleteLike(filmId, userId);
    }

    public List<Film> findAllFilms() {
        return filmStorage.getAllFilm();
    }

    public Film createFilm(Film film) throws ValidationException {
        FilmValidator.validate(film);
        return filmStorage.createFilm(film);
    }

    public Film updateFilm(Film film) throws ValidationException, NotFoundException {
        FilmValidator.validate(film);
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, film.getId());
        return filmStorage.updateFilm(film);
    }

    public List<Film> findPopularFilms(int count) {
        if (count <= 0) throw new IncorrectCountException("Значение count не может быть 0 или меньше");

        return filmStorage.getSortFilmIdsByLikes(count);
    }
}
