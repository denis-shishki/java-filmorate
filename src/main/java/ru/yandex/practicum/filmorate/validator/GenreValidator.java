package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Slf4j
@Component
public class GenreValidator {
    public static void checkCorrectVariableIdGenre(GenreDao genreDao, int id) throws NotFoundException {
        if (id <= 0) {
            throw new IncorrectCountException("Id жанра не может быть 0 или меньше");
        } else if (!genreDao.existsGenreById(id)) {
            throw new NotFoundException("Жанр с таким id не найден");
        }
    }
}

