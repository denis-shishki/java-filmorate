package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class Film {
    protected final int id;
    protected final String name;
    protected final String description;
    protected final LocalDate releaseDate;
    protected final int duration;
}
