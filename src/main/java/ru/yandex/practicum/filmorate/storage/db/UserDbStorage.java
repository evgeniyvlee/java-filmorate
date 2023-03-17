package ru.yandex.practicum.filmorate.storage.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.UserStorage;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.util.Collections;
import java.util.List;

/**
 * User storage in DB
 * @author Evgeniy Lee
 */
@Slf4j
@Component
@Qualifier("dbUserStorage")
public class UserDbStorage implements UserStorage {

    private final JdbcTemplate jdbcTemplate;

    private final String CREATE_USER_QUERY = "INSERT INTO users (name, login, email, birthday) VALUES (?, ?, ?, ?)";

    private final String GET_USER_BY_ID_QUERY = "SELECT id, name, login, email, birthday FROM users WHERE id = ?";

    private final String UPDATE_USER_QUERY = "UPDATE users SET name = ?, login = ?, email = ?, birthday = ? WHERE id = ?";

    private final String DELETE_USER_BY_ID_QUERY = "DELETE FROM users WHERE id = ?";

    private final String GET_USERS_QUERY = "SELECT id, name, login, email, birthday FROM users";

    public UserDbStorage(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void create(final User data) {
        jdbcTemplate.update(
            CREATE_USER_QUERY,
            data.getName(),
            data.getLogin(),
            data.getEmail(),
            data.getBirthday().format(DBUtils.FORMATTER)
        );
    }

    @Override
    public User get(final long id) {
        List<User> userList = jdbcTemplate.query(GET_USER_BY_ID_QUERY, DBUtils::makeUser, id);
        if (userList.isEmpty()) {
            log.error(LoggingMessages.ID_NOT_FOUND.toString(), id);
            throw new DataNotFoundException(ExceptionMessages.DATA_NOT_FOUND);
        }
        return userList.get(0);
    }

    @Override
    public void update(final User data) {
        final User user = get(data.getId());

        jdbcTemplate.update(
            UPDATE_USER_QUERY,
            data.getName(),
            data.getLogin(),
            data.getEmail(),
            data.getBirthday().format(DBUtils.FORMATTER),
            data.getId()
        );
    }

    @Override
    public void delete(final long id) {
        jdbcTemplate.update(DELETE_USER_BY_ID_QUERY, id);
    }

    @Override
    public List<User> getAll() {
        final List<User> users = jdbcTemplate.query(GET_USERS_QUERY, DBUtils::makeUser);
        return Collections.unmodifiableList(users);
    }
}
