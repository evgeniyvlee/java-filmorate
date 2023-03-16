package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.GenreStorage;
import ru.yandex.practicum.filmorate.utils.DBUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Qualifier("dbGenreStorage")
public class GenreDbStorage implements GenreStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_MPA_QUERY = "INSERT INTO genre (name) VALUES (?)";

    private final String GET_GENRE_BY_ID_QUERY = "SELECT g.id AS genre_id, g.name AS genre_name FROM genre AS g WHERE g.id = ?";

    private final String GET_ALL_GENRE_QUERY = "SELECT g.id AS genre_id, g.name AS genre_name FROM genre AS g";

    private final String UPDATE_GENRE_QUERY = "UPDATE genre SET name = ? WHERE id = ?";

    private final String DELETE_GENRE_QUERY = "DELETE FROM genre WHERE id = ?";

    public GenreDbStorage(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Genre data) {
        jdbcTemplate.update(CREATE_MPA_QUERY, data.getName());
    }

    @Override
    public Genre get(long id) {
        final List<Genre> mpaList = jdbcTemplate.query(GET_GENRE_BY_ID_QUERY, DBUtils::makeGenre, id);
        if (mpaList.isEmpty()) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        return mpaList.get(0);
    }

    @Override
    public void update(Genre data) {
        final Genre genre = get(data.getId());
        jdbcTemplate.update(UPDATE_GENRE_QUERY, data.getName(), data.getId());
    }

    @Override
    public void delete(long id) {
        final Genre genre = get(id);
        jdbcTemplate.update(DELETE_GENRE_QUERY, id);
    }

    @Override
    public List<Genre> getAll() {
        final List<Genre> genreList = jdbcTemplate.query(GET_ALL_GENRE_QUERY, DBUtils::makeGenre);
        return Collections.unmodifiableList(genreList);
    }
}
