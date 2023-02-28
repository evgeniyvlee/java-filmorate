package ru.yandex.practicum.filmorate.service.impl;

import lombok.extern.slf4j.Slf4j;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.service.UserService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.Collections;
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

    public UserServiceImpl(final Storage<User> userStorage) {
        super(userStorage);
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
        user.getFriendIds().add(friend.getId());
        friend.getFriendIds().add(user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeFriend(final long id, final long friendId) {
        final User user = super.get(id);
        final User friend = super.get(friendId);
        user.getFriendIds().remove(friend.getId());
        friend.getFriendIds().remove(user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getFriends(final long id) {
        final User user = super.get(id);
        final List<User> friends = user.getFriendIds().stream()
                .map(userId -> storage.get(userId))
                .collect(Collectors.toList());
        return Collections.unmodifiableList(friends);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> getCommonFriends(final long id, final long otherId) {
        final Set<Long> userFriendsIds = super.get(id).getFriendIds();
        final Set<Long> otherUserFriendsIds = super.get(otherId).getFriendIds();

        final List<User> commonFriends = userFriendsIds.stream()
                .filter(userId -> otherUserFriendsIds.contains(userId))
                .map(userId -> super.get(userId))
                .collect(Collectors.toList());

        return Collections.unmodifiableList(commonFriends);
    }
}
