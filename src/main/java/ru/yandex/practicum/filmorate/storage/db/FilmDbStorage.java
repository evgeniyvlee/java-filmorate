package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Data;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.FilmStorage;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
@Qualifier("dbFilmStorage")
public class FilmDbStorage implements FilmStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_FILM_QUERY = "INSERT INTO films (name, description, releasedate, duration, mpa_id) VALUES (?, ?, ?, ?, ?)";

    private final String ADD_GENRE_TO_FILM_QUERY = "INSERT INTO film_genre (film_id, genre_id) VALUES (?, ?)";

    private final String GET_FILM_BY_ID_QUERY = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name FROM films f, mpa m WHERE f.mpa_id = m.id AND f.id = ?";

    private final String DELETE_FILM_BY_ID_QUERY = "DELETE FROM films WHERE id = ?";

    private final String UPDATE_FILM_QUERY = "UPDATE films SET name = ?, description = ?, releasedate = ?, duration = ?, mpa_id = ? WHERE id = ?";

    private final String GET_FILMS_QUERY = "SELECT f.id, f.name, f.description, f.releasedate, f.duration, f.mpa_id, m.name AS mpa_name FROM films f, mpa m WHERE f.mpa_id = m.id";

    private final String GET_GENRE_BY_FILM_ID_QUERY = "SELECT fg.film_id AS film_id, g.id AS genre_id, g.name AS genre_name FROM film_genre AS fg, genre AS g WHERE fg.genre_id = g.id AND fg.film_id IN (%s)";

    private final String DELETE_FILM_GENRE_QUERY = "DELETE FROM film_genre WHERE film_id = ? AND genre_id = ?";

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

        batchUpdate(
            ADD_GENRE_TO_FILM_QUERY,
            data.getId(),
            data.getGenres().stream().map(Data::getId).collect(Collectors.toList())
        );
    }

    @Override
    public Film get(final long id) {
        List<Film> filmList = jdbcTemplate.query(GET_FILM_BY_ID_QUERY, DBUtils::makeFilm, id);
        if (filmList.isEmpty()) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        Film film = filmList.get(0);
        List<Genre> filmGenres = jdbcTemplate.query(String.format(GET_GENRE_BY_FILM_ID_QUERY, "?"), DBUtils::makeGenre, id);
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

        List<Long> filmGenreIds = film.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());
        List<Long> dataGenreIds = data.getGenres().stream().map(g -> g.getId()).collect(Collectors.toList());
        List<Long> genreIdsForInsert =
                dataGenreIds
                        .stream()
                        .filter(genreId -> !filmGenreIds.contains(genreId))
                        .collect(Collectors.toList());

        batchUpdate(ADD_GENRE_TO_FILM_QUERY, data.getId(), genreIdsForInsert);

        List<Long> genreIdsForDelete =
                filmGenreIds
                        .stream()
                        .filter(genreId -> !dataGenreIds.contains(genreId))
                        .collect(Collectors.toList());

        batchUpdate(DELETE_FILM_GENRE_QUERY, data.getId(), genreIdsForDelete);

        List<Genre> filmGenres =
            jdbcTemplate.query(String.format(GET_GENRE_BY_FILM_ID_QUERY, "?"), DBUtils::makeGenre, data.getId());
        data.setGenres(filmGenres.stream().collect(Collectors.toSet()));
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update(DELETE_FILM_BY_ID_QUERY, id);
    }

    @Override
    public List<Film> getAll() {
        final List<Film> films = jdbcTemplate.query(GET_FILMS_QUERY, DBUtils::makeFilm);
        final Map<Long, Film> filmsMap = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));

        String filmIdsStr = String.join(",", Collections.nCopies(films.size(), "?"));
        jdbcTemplate.query(
            String.format(GET_GENRE_BY_FILM_ID_QUERY, filmIdsStr),
            (rs, rowNum) ->
                filmsMap.get(
                        rs.getLong("film_id")
                ).getGenres().add(DBUtils.makeGenre(rs, rowNum))
            ,
            films.stream().map(Film::getId).collect(Collectors.toList()).toArray()
        );
        return Collections.unmodifiableList(films);
    }

    private int[] batchUpdate(final String updateQuery, final long filmId, final List<Long> genreIds) {
        int[] updateCounts = jdbcTemplate.batchUpdate(
                updateQuery,
                new BatchPreparedStatementSetter() {
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, filmId);
                        ps.setLong(2, genreIds.get(i));
                    }

                    public int getBatchSize() {
                        return genreIds.size();
                    }
                } );
        return updateCounts;
    }
}
