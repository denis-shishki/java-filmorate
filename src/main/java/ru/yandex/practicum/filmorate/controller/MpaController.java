package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.service.MpaService;

import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/mpa")
public class MpaController {
    private final MpaService mpaService;

    public MpaController(MpaService mpaService) {
        this.mpaService = mpaService;
    }

    @GetMapping()
    public List<Mpa> findAllMpa() {
        log.info("Получен запрос GET /mpa.");
        return mpaService.findAllMpa();
    }

    @GetMapping("{id}")
    public Mpa findMpaById(@PathVariable int id) throws NotFoundException {
        log.info("Получен запрос GET /mpa/{id}.");
        return mpaService.findMpaById(id);
    }
}
