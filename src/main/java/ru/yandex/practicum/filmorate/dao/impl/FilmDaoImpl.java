package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.util.Collections;
import java.util.List;

@Slf4j
@Component
public class FilmDaoImpl implements FilmDao {

    private final JdbcTemplate jdbcTemplate;

    private final static String ADD_LIKE_QUERY = "INSERT INTO film_like (film_id, user_id) VALUES (?, ?)";

    private final static String DELETE_LIKE_QUERY = "DELETE FROM film_like WHERE film_id = ? AND user_id = ?";

    private final static String GET_POPULAR_FILMS_QUERY =
            "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name, COUNT(f.id) " +
            "FROM films f " +
                "JOIN mpa m ON f.mpa_id = m.id " +
                "LEFT JOIN film_like AS fl ON f.id = fl.film_id " +
            "GROUP BY f.name " +
            "ORDER BY COUNT(f.id) DESC, f.id DESC " +
            "LIMIT ?";

    public FilmDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addLike(final long id, final long userId) {
        jdbcTemplate.update(ADD_LIKE_QUERY, id, userId);
    }

    @Override
    public void removeLike(long id, long userId) {
        jdbcTemplate.update(DELETE_LIKE_QUERY, id, userId);
    }

    @Override
    public List<Film> getPopular(int count) {
        final List<Film> films = jdbcTemplate.query(GET_POPULAR_FILMS_QUERY, (rs, rowNum) -> DBUtils.makeFilm(rs), count);
        return Collections.unmodifiableList(films);
    }
}
