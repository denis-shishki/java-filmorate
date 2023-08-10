package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.FilmValidator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController {
    private final HashMap<Integer, Film> films = new HashMap<>();
    private int nextId = 1;

    @GetMapping()
    public List<Film> findAllUsers() {
        log.info("Получен запрос GET /films.");
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос POST /films.");
        FilmValidator.validate(film);
        Film newFilm = Film.builder()
                .id(nextId++)
                .name(film.getName())
                .description(film.getDescription())
                .releaseDate(film.getReleaseDate())
                .duration(film.getDuration())
                .build();
        films.put(newFilm.getId(), newFilm);
        return newFilm;
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос PUT /films.");
        if (!films.containsKey(film.getId())){
            throw new FilmNotFoundException("Фильма с данным идентификатором нет в базе");
        }
        FilmValidator.validate(film);
        films.put(film.getId(), film);
        return film;
    }

//    private void checkValidationFilm(Film film) throws ValidationException {
//        if ((film.getId() == 0 || films.containsKey(film.getId())) //если у фильма есть id, проверяем его наличие в мапе
//                && !film.getName().isBlank()
//                && film.getDescription().length() <= 200
//                && film.getReleaseDate().isAfter(MOVIE_BIRTHDAY)
//                && film.getDuration() >= 0){
//            return;
//        } else {
//            log.warn("Некорректный ввод данных");
//            throw new ValidationException("Некорректный ввод данных");
//        }
//    }
}
