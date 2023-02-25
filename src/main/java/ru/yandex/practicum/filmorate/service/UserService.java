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
     * @throws DataNotFoundException occurs if user or friend not found
     */
    void addFriend(long id, long friendId) throws DataNotFoundException;

    /**
     * Remove friend to user
     * @param id user ID
     * @param friendId friend ID
     * @throws DataNotFoundException occurs if user or friend not found
     */
    void removeFriend(long id, long friendId) throws DataNotFoundException;

    /**
     * Get list of friends for user with ID
     * @param id user ID
     * @return list of friends
     * @throws DataNotFoundException occurs if user not found
     */
    List<User> getFriends(long id) throws DataNotFoundException;

    /**
     * Get common friends for users with IDs
     * @param id user ID
     * @param otherId other user ID
     * @return list of common friends
     * @throws DataNotFoundException if user or other user not found
     */
    List<User> getCommonFriends(long id, long otherId) throws DataNotFoundException;
}
