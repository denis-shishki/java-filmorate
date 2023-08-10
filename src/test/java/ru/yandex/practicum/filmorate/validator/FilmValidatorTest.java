package ru.yandex.practicum.filmorate.validator;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.Valid;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FilmValidatorTest {

    @Test
    void filmTry(){
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        assertDoesNotThrow(() -> FilmValidator.validate(film));
    }

    @Test
    void filmFailName (){
        Film film = Film.builder()
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () ->FilmValidator.validate(film));
        assertEquals("Имя не может быть пустым.", exception.getMessage());
    }

    @Test
    void filmFailDescription (){
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("Пятеро друзей ( комик-группа «Шарло»), приезжают в город Бризуль. Здесь они хотят разыскать господина Огюста Куглова, который задолжал им деньги, а именно 20 миллионов. о Куглов, который за время «своего отсутствия», стал кандидатом Коломбани.")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () ->FilmValidator.validate(film));
        assertEquals("Описание не должн превышать 200 символов.", exception.getMessage());
    }

    @Test
    void filmFailReleaseDate (){
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1567, 3, 25))
                .duration(100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () ->FilmValidator.validate(film));
        assertEquals("Дата релиза не может быть раньше 28-12-1895.", exception.getMessage());
    }

    @Test
    void filmFailDuration (){
        Film film = Film.builder()
                .name("nisi eiusmod")
                .description("adipisicing")
                .releaseDate(LocalDate.of(1967, 3, 25))
                .duration(-100)
                .build();
        ValidationException exception = assertThrows(ValidationException.class, () ->FilmValidator.validate(film));
        assertEquals("Длительность не может быть отрицательной", exception.getMessage());

    }
}