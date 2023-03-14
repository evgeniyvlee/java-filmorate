package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.dao.UserDao;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

/**
 * User service provides operations of creating, updating and getting for user data
 * @author Evgeniy Lee
 */
@Slf4j
@org.springframework.stereotype.Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    private final UserDao userDao;

    public UserServiceImpl(@Qualifier("dbUserStorage") final Storage<User> userStorage, final UserDao userDao) {
        super(userStorage);
        this.userDao = userDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
        super.create(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addFriend(final long id, final long friendId) {
        final User user = super.get(id);
        final User friend = super.get(friendId);
        userDao.addFriend(user.getId(), friend.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFriend(final long id, final long friendId) {
        final User user = super.get(id);
        final User friend = super.get(friendId);
        userDao.removeFriend(user.getId(), friend.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getFriends(final long id) {
        final User user = super.get(id);
        return userDao.getFriends(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getCommonFriends(final long id, final long otherId) {
        final User user = super.get(id);
        final User otherUser = super.get(otherId);
        return userDao.getCommonFriends(user.getId(), otherUser.getId());
    }
}
