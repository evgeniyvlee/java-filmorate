package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.DataAlreadyExistException;
import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * User service provides operations of creating, updating and getting for user data
 * @author Evgeniy Lee
 */
@Slf4j
@org.springframework.stereotype.Service
public class UserServiceImpl extends AbstractService<User> implements UserService {

    @Autowired
    public UserServiceImpl(final Storage<User> userStorage) {
        super(userStorage);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public User create(final User user) throws DataAlreadyExistException {
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
    public void addFriend(final long id, final long friendId) throws DataNotFoundException {
        final User user = super.get(id);
        final User friend = super.get(friendId);
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFriend(final long id, final long friendId) throws DataNotFoundException {
        final User user = super.get(id);
        final User friend = super.get(friendId);
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getFriends(final long id) throws DataNotFoundException {
        final User user = super.get(id);
        return storage.getAll().stream()
                .filter(friend -> user.getFriendIds().contains(friend.getId()))
                .filter(friend -> friend.getId() != user.getId())
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getCommonFriends(final long id, final long otherId) throws DataNotFoundException {
        final Set<Long> commonFriendIds = new HashSet<>(super.get(id).getFriendIds());
        commonFriendIds.retainAll(super.get(otherId).getFriendIds());

        final List<User> commonFriends = super.getAll().stream()
                .filter(user -> commonFriendIds.contains(user.getId()))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(commonFriends);
    }
}
