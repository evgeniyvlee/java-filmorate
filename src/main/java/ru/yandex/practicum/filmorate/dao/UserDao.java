package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.User;
import java.util.List;

/**
 * User DAO interface provides methods for users.
 * @author Evgeniy Lee
 */
public interface UserDao {
    /**
     * Add friendship between users
     * @param id user ID
     * @param friendId friend ID
     */
    void addFriend(long id, long friendId);

    /**
     * Remove friendship between users
     * @param id user ID
     * @param friendId friend ID
     */
    void removeFriend(long id, long friendId);

    /**
     * Get friends for user
     * @param id user ID
     * @return friends
     */
    List<User> getFriends(long id);

    /**
     * Get common friends for users
     * @param id user ID
     * @param otherId other user ID
     * @return list of common friends
     */
    List<User> getCommonFriends(long id, long otherId);
}
