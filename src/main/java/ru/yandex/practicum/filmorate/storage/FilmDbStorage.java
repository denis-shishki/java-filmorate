package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.dao.MpaDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class FilmDbStorage implements FilmDao {
    protected final JdbcTemplate jdbcTemplate;
    protected final GenreDao genreDao;
    protected final MpaDao mpaDao;

    public FilmDbStorage(JdbcTemplate jdbcTemplate, GenreDao genreDao, MpaDao mpaDao) {
        this.jdbcTemplate = jdbcTemplate;
        this.genreDao = genreDao;
        this.mpaDao = mpaDao;
    }

    @Override
    public List<Film> getAllFilm() {
        String sqlQuery = "select f.*, m.* from films as f join mpa as m on f.mpa_id = m.mpa_id";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm);
    }

    @Override
    public Film createFilm(Film film) {
        String sqlQuery = "insert into films (name, description, release_date, duration, mpa_id) " +
                "values (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sqlQuery, new String[]{"film_id"});
            ps.setString(1, film.getName());
            ps.setString(2, film.getDescription());
            ps.setDate(3, Date.valueOf(film.getReleaseDate()));
            ps.setInt(4, film.getDuration());
            ps.setInt(5, film.getMpa().getId());
            return ps;
        }, keyHolder);

        int filmId = Objects.requireNonNull(keyHolder.getKey()).intValue();
        film.setId(filmId);

        List<Genre> genres = film.getGenres();
        if (genres != null) {

            List<Genre> uniqueGenres = new ArrayList<>();
            for (Genre genre : genres) {
                if (!uniqueGenres.contains(genre)) {
                    uniqueGenres.add(genre);
                }
            }
            film.setGenres(uniqueGenres);
            genreDao.createConnectionGenreWithFilm(filmId, uniqueGenres);
        }
        return film;
    }

    @Override
    public Film updateFilm(Film film) {
        String sqlQuery = "update films set name = ?, description = ?, release_date = ?, duration = ?, mpa_id = ?" +
                " where film_id = ?";
        jdbcTemplate.update(sqlQuery,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getMpa().getId(),
                film.getId()
        );

        deleteAllGenreByFilmId(film.getId());

        List<Genre> genres = film.getGenres();
        if (genres != null) {

            List<Genre> uniqueGenres = new ArrayList<>();
            for (Genre genre : genres) {
                if (!uniqueGenres.contains(genre)) {
                    uniqueGenres.add(genre);
                }
            }
            film.setGenres(uniqueGenres);
            genreDao.createConnectionGenreWithFilm(film.getId(), uniqueGenres);
        }
        return film;
    }

    @Override
    public void deleteAllGenreByFilmId(int id) {
        String sqlQuery = "delete from films_genre where film_id = ?";
        jdbcTemplate.update(sqlQuery, id);
    }

    @Override
    public void addLike(int filmId, int userId) {
        String sqlQuery = "insert into likes_list (film_id, user_id) values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public List<Film> getSortFilmIdsByLikes(int count) {
        String sqlQuery = "SELECT f.*, m.* " +
                "FROM films f " +
                "join mpa as m on f.mpa_id = m.mpa_id " +
                "LEFT JOIN likes_list l ON f.film_id=l.film_id " +
                "GROUP BY f.film_id " +
                "ORDER BY COUNT(l.user_id) DESC " +
                "LIMIT ?";
        return jdbcTemplate.query(sqlQuery, this::mapRowToFilm, count);
    }

    @Override
    public void deleteLike(int filmId, int userId) {
        String sqlQuery = "delete from likes_list where film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, filmId, userId);
    }

    @Override
    public Film getFilmById(int id) {
        String sqlQuery = "select f.*, m.* from films as f join mpa as m on f.mpa_id = m.mpa_id where film_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToFilm, id);
    }

    private Film mapRowToFilm(ResultSet rs, int rowNum) throws SQLException {
        List<Genre> genres = genreDao.findGenresByPostId(rs.getInt("film_id"));
        return Film.builder()
                .id(rs.getInt("films.film_id"))
                .duration(rs.getInt("films.duration"))
                .releaseDate(rs.getDate("films.release_date").toLocalDate())
                .description(rs.getString("films.description"))
                .name(rs.getString("films.name"))
                .mpa(Mpa.builder()
                        .id(rs.getInt("mpa.mpa_id"))
                        .name(rs.getString("mpa.name"))
                        .build())
                .genres(genres)
                .build();
    }

    @Override
    public boolean existsFilmById(int filmId) {
        String sql = "select count(*) from films where film_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, filmId);
        if (count == null) {
            return false;
        }
        return count > 0;
    }
}
