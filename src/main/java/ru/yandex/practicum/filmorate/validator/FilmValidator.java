package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.time.LocalDate;
import java.util.HashSet;

@Slf4j
@Component
public class FilmValidator {
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public static void validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Некорректный ввод данных: Имя не может быть пустым.");
            throw new ValidationException("Имя не может быть пустым.");
        } else if (film.getDescription().length() >= 200) {
            log.warn("Некорректный ввод данных: Описание не должно превышать 200 символов.");
            throw new ValidationException("Описание не должно превышать 200 символов.");
        } else if (film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
            log.warn("Некорректный ввод данных: Дата релиза не может быть раньше 28-12-1895.");
            throw new ValidationException("Дата релиза не может быть раньше 28-12-1895.");
        } else if (film.getDuration() < 0) {
            log.warn("Некорректный ввод данных: Длительность не может быть отрицательной");
            throw new ValidationException("Длительность не может быть отрицательной");
        } else if (film.getIdsUsersLike() == null) {
            film.setIdsUsersLike(new HashSet<>());
        }
    }

    public static void checkCorrectVariableIdFilm(FilmStorage filmStorage, int id) throws NotFoundException {
        if (id <= 0) {
            throw new IncorrectCountException("Id фильма не может быть 0 или меньше");
        } else if (filmStorage.getFilmById(id) == null) {
            throw new NotFoundException("Фильм с таким id не найден");
        }
    }
}

