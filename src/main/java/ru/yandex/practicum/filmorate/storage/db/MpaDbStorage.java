package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.Mpa;
import ru.yandex.practicum.filmorate.storage.MpaStorage;
import ru.yandex.practicum.filmorate.utils.DBUtils;

import java.util.Collections;
import java.util.List;

@Slf4j
@Component
@Qualifier("dbMpaStorage")
public class MpaDbStorage implements MpaStorage {

    private final JdbcTemplate jdbcTemplate;

    private static final String CREATE_MPA_QUERY = "INSERT INTO mpa (name) VALUES (?)";

    private final static String GET_MPA_BY_ID_QUERY = "SELECT m.id AS mpa_id, m.name AS mpa_name FROM mpa AS m WHERE m.id = ?";

    private final static String GET_ALL_MPA_QUERY = "SELECT m.id AS mpa_id, m.name AS mpa_name FROM mpa AS m";

    private static final String UPDATE_MPA_QUERY = "UPDATE mpa SET name = ? WHERE id = ?";

    private static final String DELETE_MPA_QUERY = "DELETE FROM mpa WHERE id = ?";

    public MpaDbStorage(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(Mpa data) {
        jdbcTemplate.update(CREATE_MPA_QUERY, data.getName());
    }

    @Override
    public Mpa get(long id) {
        final List<Mpa> mpaList = jdbcTemplate.query(GET_MPA_BY_ID_QUERY, (rs, rowNum) -> DBUtils.makeMpa(rs), id);
        if (mpaList.isEmpty()) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        return mpaList.get(0);
    }

    @Override
    public void update(Mpa data) {
        final Mpa mpa = get(data.getId());
        jdbcTemplate.update(UPDATE_MPA_QUERY, data.getName(), data.getId());
    }

    @Override
    public void delete(long id) {
        final Mpa mpa = get(id);
        jdbcTemplate.update(DELETE_MPA_QUERY, id);
    }

    @Override
    public List<Mpa> getAll() {
        final List<Mpa> mpaList = jdbcTemplate.query(GET_ALL_MPA_QUERY, (rs, rowNum) -> DBUtils.makeMpa(rs));
        return Collections.unmodifiableList(mpaList);
    }
}
