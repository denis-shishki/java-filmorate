package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.InMemoryFilmStorage;
import ru.yandex.practicum.filmorate.storage.InMemoryUserStorage;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FilmService {

    private final InMemoryFilmStorage filmStorage;
    private final InMemoryUserStorage userStorage;

    @Autowired
    public FilmService(InMemoryFilmStorage inMemoryFilmStorage, InMemoryUserStorage inMemoryUserStorage) {
        this.filmStorage = inMemoryFilmStorage;
        this.userStorage = inMemoryUserStorage;
    }

    public Film likeFilm(int idFilm, int idUser) throws NotFoundException {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, idFilm);
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);

        Film film = filmStorage.getFilmById(idFilm);
        User user = userStorage.getUserById(idUser);

        film.addLike(user.getId());
        return film;
    }

    public Film getFilmById(int id) throws NotFoundException {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, id);
        return filmStorage.getFilmById(id);
    }

    public Film deleteLike(int idFilm, int idUser) throws Exception {
        FilmValidator.checkCorrectVariableIdFilm(filmStorage, idFilm);
        UserValidator.checkCorrectVariableIdUser(userStorage, idUser);

        Film film = filmStorage.getFilmById(idFilm);
        User user = userStorage.getUserById(idUser);

        film.deleteLike(user.getId());
        return film;
    }

    public List<Film> findPopularFilms(int count) {
        List<Film> films = filmStorage.getAllFilm();
        return films.stream()
                .sorted(Comparator.comparing(Film::getNumberOfLikes, Comparator.reverseOrder()))
                .limit(count)
                .collect(Collectors.toList());
    }
}
