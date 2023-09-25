package ru.yandex.practicum.filmorate.validator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.exception.NotFoundException;

@Slf4j
@Component
public class MpaValidator {
    public static void checkCorrectVariableIdMpa(MpaDao mpaDao, int id) throws NotFoundException {
        if (id <= 0) {
            throw new IncorrectCountException("Id рейтинга не может быть 0 или меньше");
        } else if (!mpaDao.existsMpaById(id)) {
            throw new NotFoundException("Рейтинг с таким id не найден");
        }
    }
}

