package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class Film {
    protected int id;
    protected final String name;
    protected final String description;
    protected final LocalDate releaseDate;
    protected final int duration;
    protected List<Genre> genres;
    protected final Mpa mpa;
}
