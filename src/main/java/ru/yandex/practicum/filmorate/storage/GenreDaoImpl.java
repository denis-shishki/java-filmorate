package ru.yandex.practicum.filmorate.storage;

import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.GenreDao;
import ru.yandex.practicum.filmorate.model.Genre;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
public class GenreDaoImpl implements GenreDao {
    protected JdbcTemplate jdbcTemplate;

    public GenreDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Genre findGenreById(int id) {
        String sqlQuery = "select * from genre where genre_id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public void createConnectionGenreWithFilm(int filmId, int genreId) {
        String sqlQuery = "insert into films_genre (film_id, genre_id) " +
                "values (?, ?)";
        jdbcTemplate.update(sqlQuery, filmId, genreId);
    }

    @Override
    public void createConnectionGenreWithFilm(int filmId, List<Genre> genres) {
        String sqlQuery = "insert into films_genre (film_id, genre_id) " +
                "values (?, ?)";

        jdbcTemplate.batchUpdate(sqlQuery,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        Genre genre = genres.get(i);
                        ps.setInt(1, filmId);
                        ps.setInt(2, genre.getId());
                    }

                    public int getBatchSize() {
                        return genres.size();
                    }
                });
    }

    @Override
    public List<Genre> findAllGenres() {
        String sqlQuery = "select * from genre";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public List<Genre> findGenresByPostId(int id) {
        String sqlQuery = "select * from genre where genre_id in (select genre_id from films_genre where film_id = ?)";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public boolean existsGenreById(int id) {
        String sql = "select count(*) from genre where genre_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, id);
        if (count == null) {
            return false;
        }
        return count > 0;
    }

    protected Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder()
                .id(rs.getInt("genre_id"))
                .name(rs.getString("name"))
                .build();
    }
}
