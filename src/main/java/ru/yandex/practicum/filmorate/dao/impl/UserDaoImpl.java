package ru.yandex.practicum.filmorate.dao.impl;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.utils.DBUtils;
import java.util.Collections;
import java.util.List;

@Component
public class UserDaoImpl implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    private static final String ADD_FRIEND_QUERY = "INSERT INTO friendship (user_id, friend_id) VALUES (?, ?)";

    private static final String DELETE_FRIEND_QUERY = "DELETE FROM friendship WHERE user_id = ? AND friend_id = ?";

    private static final String GET_FRIENDS_QUERY =
        "SELECT id, name, login, email, birthday " +
        "FROM users WHERE users.id IN " +
                "(SELECT friend_id FROM friendship WHERE user_id = ?)";

    private static final String GET_COMMON_FRIENDS_QUERY =
            "SELECT u.id, u.name, u.login, u.email, u.birthday " +
            "FROM users u " +
            "WHERE u.id IN (" +
                    "SELECT f.friend_id " +
                    "FROM friendship f, friendship f2 " +
                    "WHERE f.user_id = ? AND f2.user_id = ? AND f.friend_id = f2.friend_id" +
            ")";

    public UserDaoImpl(final JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void addFriend(long id, long friendId) {
        jdbcTemplate.update(ADD_FRIEND_QUERY, id, friendId);
    }

    @Override
    public void removeFriend(long id, long friendId) {
        jdbcTemplate.update(DELETE_FRIEND_QUERY, id, friendId);
    }

    @Override
    public List<User> getFriends(long id) {
        final List<User> friends = jdbcTemplate.query(GET_FRIENDS_QUERY, (rs, rowNum) -> DBUtils.makeUser(rs), id);
        return Collections.unmodifiableList(friends);
    }

    @Override
    public List<User> getCommonFriends(long id, long otherId) {
        final List<User> commonFriends =
            jdbcTemplate.query(GET_COMMON_FRIENDS_QUERY, (rs, rowNum) -> DBUtils.makeUser(rs), id, otherId);
        return Collections.unmodifiableList(commonFriends);
    }
}
