package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.validator.GenreValidator;

import java.util.List;

@Service
public class GenreService {

    private final GenreDao genreDao;

    @Autowired
    public GenreService(GenreDao genreDao) {
        this.genreDao = genreDao;
    }

    public Genre findGenreById(int id) throws NotFoundException {
        GenreValidator.checkCorrectVariableIdGenre(genreDao, id);
        return genreDao.findGenreById(id);
    }

    public List<Genre> findAllGenre() {
        return genreDao.findAllGenres();
    }
}
