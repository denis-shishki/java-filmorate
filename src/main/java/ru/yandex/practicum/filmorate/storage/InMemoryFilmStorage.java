package ru.yandex.practicum.filmorate.storage;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @Override
    public List<Film> findAllFilms() {
        return new ArrayList<>(films.values());
    }

    @Override
    public Film createFilm(Film film) throws ValidationException {
        FilmValidator.validate(film);
        Film newFilm = Film.builder()
                .id(nextId++)
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .idsUsersLike(film.getIdsUsersLike())
                .build();
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @Override
    public Film updateFilm(Film film) throws ValidationException, FilmNotFoundException {
        if (films.containsKey(film.getId())) {
            FilmValidator.validate(film);
            films.put(film.getId(), film);
            return film;
        } else {
            throw new FilmNotFoundException("Фильма с данным идентификатором нет в базе");
        }
    }

    public Film getFilmById(int id) {
        return films.get(id);
    }
}
