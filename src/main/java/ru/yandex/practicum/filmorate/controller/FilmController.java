package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.FilmNotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.FilmStorage;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController {

    private final FilmStorage filmStorage;
    private final FilmService filmService;

    @Autowired
    public FilmController(FilmStorage filmStorage, FilmService filmService) {
        this.filmStorage = filmStorage;
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> findAllFilms() {
        log.info("Получен запрос GET /films.");
        return filmStorage.findAllFilms();
    }


    @GetMapping("{id}")
    public Film findFilmById(@PathVariable int id) throws FilmNotFoundException {
        log.info("Получен запрос GET /films/{id}.");
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос POST /films.");
        return filmStorage.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос PUT /films.");
        return filmStorage.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public Film likeFilm(@PathVariable int id, @PathVariable int userId) throws Exception {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        return filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public Film deleteLike(@PathVariable int id, @PathVariable int userId) throws Exception {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");

        return filmService.deleteLike(id, userId);
    }

    @RequestMapping("/popular")
    @GetMapping
    public List<Film> findPopularFilms(@RequestParam(defaultValue = "10") int count) throws IncorrectCountException {
        log.info("Получен запрос GET /films/popular.");

        if (count <= 0) {
            throw new IncorrectCountException("Значение count не может быть 0 или меньше");
        }
        return filmService.findPopularFilms(count);
    }
}
