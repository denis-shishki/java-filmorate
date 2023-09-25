package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.validator.FilmValidator;
import ru.yandex.practicum.filmorate.validator.MpaValidator;
import ru.yandex.practicum.filmorate.validator.UserValidator;

import java.util.List;

@Service
public class MpaService {

    private final MpaDao mpaStorage;

    @Autowired
    public MpaService(MpaDao mpaStorage) {
        this.mpaStorage = mpaStorage;
    }

    public Mpa findMpaById(int id) throws NotFoundException {
        MpaValidator.checkCorrectVariableIdMpa(mpaStorage, id);
        return mpaStorage.findMpaById(id);
    }

    public List<Mpa> findAllMpa() {
        return mpaStorage.findAllMpa();
    }
}
