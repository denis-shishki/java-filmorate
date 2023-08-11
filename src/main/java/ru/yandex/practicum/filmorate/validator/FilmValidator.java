package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

@Slf4j
public class FilmValidator {
    private static final LocalDate MOVIE_BIRTHDAY = LocalDate.of(1895, 12, 28);

    public static void validate(Film film) throws ValidationException {
        if (film.getName() == null || film.getName().isBlank()) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Имя не может быть пустым.");
        } else if (film.getDescription().length() >= 200) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Описание не должн превышать 200 символов.");
        } else if (film.getReleaseDate().isBefore(MOVIE_BIRTHDAY)) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Дата релиза не может быть раньше 28-12-1895.");
        } else if (film.getDuration() < 0) {
            log.warn("Некорректный ввод данных");
            throw new ValidationException("Длительность не может быть отрицательной");
        }
    }
}

