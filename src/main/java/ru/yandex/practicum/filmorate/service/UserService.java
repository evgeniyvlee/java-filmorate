package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

/**
 * UserService interface provides additional functions for users
 * @author Evgeniy Lee
 */
public interface UserService extends Service<User> {
    /**
     * Add friend to user
     * @param id user ID
     * @param friendId friend ID
     */
    void addFriend(long id, long friendId);

    /**
     * Remove friend to user
     * @param id user ID
     * @param friendId friend ID
     */
    void removeFriend(long id, long friendId);

    /**
     * Get list of friends for user with ID
     * @param id user ID
     * @return list of friends
     */
    List<User> getFriends(long id);

    /**
     * Get common friends for users with IDs
     * @param id user ID
     * @param otherId other user ID
     * @return list of common friends
     */
    List<User> getCommonFriends(long id, long otherId);
}
