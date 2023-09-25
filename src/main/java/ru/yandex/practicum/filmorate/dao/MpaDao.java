package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Mpa;

import java.util.List;

public interface MpaDao {
    public Mpa findMpaById(int id);

    public List<Mpa> findAllMpa();

    boolean existsMpaById(int id);
}
