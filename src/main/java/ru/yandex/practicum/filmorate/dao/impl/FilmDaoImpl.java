package ru.yandex.practicum.filmorate.dao.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.util.Collections;
import java.util.List;

/**
 * Film DAO implementation
 * @author Evgeniy Lee
 */
@Slf4j
@Component
public class FilmDaoImpl implements FilmDao {
    // JDBC template instance
    private final JdbcTemplate jdbcTemplate;
    // Query for adding like to film
    private final String ADD_LIKE_QUERY = "INSERT INTO film_like (film_id, user_id) VALUES (?, ?)";
    // Query for deleting like film
    private final String DELETE_LIKE_QUERY = "DELETE FROM film_like WHERE film_id = ? AND user_id = ?";
    // Query for getting popular films
    private final String GET_POPULAR_FILMS_QUERY =
            "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name, COUNT(f.id) " +
            "FROM films f " +
                "JOIN mpa m ON f.mpa_id = m.id " +
                "LEFT JOIN film_like AS fl ON f.id = fl.film_id " +
            "GROUP BY f.name " +
            "ORDER BY COUNT(f.id) DESC, f.id DESC " +
            "LIMIT ?";

    /**
     * Constructor
     * @param jdbcTemplate JDBC template instance
     */
    public FilmDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLike(final long id, final long userId) {
        jdbcTemplate.update(ADD_LIKE_QUERY, id, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLike(final long id, final long userId) {
        jdbcTemplate.update(DELETE_LIKE_QUERY, id, userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Film> getPopular(final int count) {
        final List<Film> films = jdbcTemplate.query(GET_POPULAR_FILMS_QUERY, DBUtils::makeFilm, count);
        return Collections.unmodifiableList(films);
    }
}
