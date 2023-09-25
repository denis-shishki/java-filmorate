package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.IncorrectCountException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.dao.FilmDao;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/films")
public class FilmController {

    private final FilmService filmService;

    public FilmController(FilmService filmService) {
        this.filmService = filmService;
    }

    @GetMapping()
    public List<Film> findAllFilms() {
        log.info("Получен запрос GET /films.");
        return filmService.findAllFilms();
    }


    @GetMapping("{id}")
    public Film findFilmById(@PathVariable int id) throws NotFoundException {
        log.info("Получен запрос GET /films/{id}.");
        return filmService.getFilmById(id);
    }

    @PostMapping
    public Film createFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос POST /films.");
        return filmService.createFilm(film);
    }

    @PutMapping
    public Film updateFilm(@RequestBody Film film) throws Exception {
        log.info("Получен запрос PUT /films.");
        return filmService.updateFilm(film);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likeFilm(@PathVariable int id, @PathVariable int userId) throws Exception {
        log.info("Получен запрос PUT /films/{id}/like/{userId}.");
        filmService.likeFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(@PathVariable int id, @PathVariable int userId) throws Exception {
        log.info("Получен запрос DELETE /films/{id}/like/{userId}.");
        filmService.deleteLike(id, userId);
    }

    @RequestMapping("/popular")
    @GetMapping
    public List<Film> findPopularFilms(@RequestParam(defaultValue = "10") int count) throws IncorrectCountException {
        log.info("Получен запрос GET /films/popular.");
        return filmService.findPopularFilms(count);
    }
}
