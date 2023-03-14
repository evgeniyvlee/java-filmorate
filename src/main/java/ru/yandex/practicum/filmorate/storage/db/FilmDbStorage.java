package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("dbFilmStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final static String CREATE_FILM_QUERY = "INSERT INTO films (name, description, releasedate, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";

    private final static String ADD_GENRE_TO_FILM_QUERY = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";

    private final static String GET_FILM_BY_ID_QUERY = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name FROM films f, mpa m WHERE f.mpa_id = m.id AND f.id = ?";

    private final static String DELETE_FILM_BY_ID_QUERY = "DELETE FROM films WHERE id = ?";

    private final static String UPDATE_FILM_QUERY = "UPDATE films SET name = ?, description = ?, releasedate = ?, duration = ?, mpa_id = ? WHERE id = ?";

    private final static String GET_FILMS_QUERY = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name FROM films f, mpa m WHERE f.mpa_id = m.id";

    private final static String GET_GENRE_BY_FILM_ID_QUERY = "SELECT g.id AS genre_id, g.name AS genre_name FROM film_genre AS fg, genre AS g WHERE fg.genre_id = g.id AND fg.film_id = ?";

    private final static String DELETE_FILM_GENRE_QUERY = "DELETE FROM film_genre WHERE film_id = ? AND genre_id = ?";

    public FilmDbStorage(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(final Film data) {
        jdbcTemplate.update(
            CREATE_FILM_QUERY,
            data.getName(),
            data.getDescription(),
            data.getReleaseDate().format(DBUtils.FORMATTER),
            data.getDuration(),
            data.getMpa().getId()
        );

        for (Genre genre : data.getGenres()) {
            jdbcTemplate.update(
                ADD_GENRE_TO_FILM_QUERY,
                data.getId(),
                genre.getId()
            );
        }
    }

    @Override
    public Film get(final long id) {
        List<Film> filmList = jdbcTemplate.query(GET_FILM_BY_ID_QUERY, (rs, rowNum) -> DBUtils.makeFilm(rs), id);
        if (filmList.isEmpty()) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        Film film = filmList.get(0);
        List<Genre> filmGenres = jdbcTemplate.query(GET_GENRE_BY_FILM_ID_QUERY, (rs, rowNum) -> DBUtils.makeGenre(rs), id);
        film.getGenres().addAll(filmGenres);
        return film;
    }

    @Override
    public void update(final Film data) {
        final Film film = get(data.getId());

        jdbcTemplate.update(
            UPDATE_FILM_QUERY,
            data.getName(),
            data.getDescription(),
            data.getReleaseDate().format(DBUtils.FORMATTER),
            data.getDuration(),
            data.getMpa().getId(),
            data.getId()
        );

        Set<Long> filmGenreIds = film.getGenres().stream().map(g -> g.getId()).collect(Collectors.toSet());
        Set<Long> dataGenreIds = data.getGenres().stream().map(g1 -> g1.getId()).collect(Collectors.toSet());
        Set<Long> genreIdsForInsert =
                dataGenreIds
                        .stream()
                        .filter(genreId -> !filmGenreIds.contains(genreId))
                        .collect(Collectors.toSet());

        for (Long genreId : genreIdsForInsert) {
            jdbcTemplate.update(ADD_GENRE_TO_FILM_QUERY, data.getId(), genreId);
        }

        Set<Long> genreIdsForDelete =
                filmGenreIds
                        .stream()
                        .filter(genreId -> !dataGenreIds.contains(genreId))
                        .collect(Collectors.toSet());

        for (Long genreId : genreIdsForDelete) {
            jdbcTemplate.update(DELETE_FILM_GENRE_QUERY, data.getId(), genreId);
        }

        List<Genre> filmGenres = jdbcTemplate.query(GET_GENRE_BY_FILM_ID_QUERY, (rs, rowNum) -> DBUtils.makeGenre(rs), data.getId());
        data.setGenres(filmGenres);
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update(DELETE_FILM_BY_ID_QUERY, id);
    }

    @Override
    public List<Film> getAll() {
        final List<Film> films = jdbcTemplate.query(GET_FILMS_QUERY, (rs, rowNum) -> DBUtils.makeFilm(rs));
        for (Film film : films) {
            List<Genre> filmGenres = jdbcTemplate.query(GET_GENRE_BY_FILM_ID_QUERY, (rs, rowNum) -> DBUtils.makeGenre(rs), film.getId());
            film.getGenres().addAll(filmGenres);
        }
        return Collections.unmodifiableList(films);
    }
}
