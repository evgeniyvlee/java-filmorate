package ru.yandex.practicum.filmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.yandex.practicum.filmorate.messages.LoggingMessages;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.UserService;
import javax.validation.Valid;
import java.util.List;

/**
 * Controller provides methods for creating, updating and getting users from storage.
 * @author Evgeniy Lee
 */
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController implements Controller<User> {
    // User service
    private final UserService service;

    /**
     * Constructor
     * @param service user service instance
     */
    public UserController(final UserService service) {
        this.service = service;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping
    public List<User> getAll() {
        log.info(LoggingMessages.GET.toString());
        return service.getAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PostMapping
    public User create(@Valid @RequestBody User user) {
        log.info(LoggingMessages.CREATE.toString(), user);
        return service.create(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @PutMapping
    public User update(@Valid @RequestBody User user) {
        log.info(LoggingMessages.UPDATE.toString(), user);
        return service.update(user);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @GetMapping("{id}")
    public User get(@PathVariable long id) {
        log.info(LoggingMessages.GET_BY_ID.toString(), id);
        return service.get(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @DeleteMapping
    public void delete(@PathVariable long id) {
        log.info(LoggingMessages.DELETE_BY_ID.toString(), id);
        service.delete(id);
    }

    /**
     * Add friend to user
     * @param id user ID
     * @param friendId friend ID
     */
    @PutMapping("{id}/friends/{friendId}")
    public void addFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(LoggingMessages.ADD_FRIEND.toString(), id, friendId);
        service.addFriend(id, friendId);
    }

    /**
     * Remove friend to user
     * @param id user ID
     * @param friendId friend ID
     */
    @DeleteMapping("{id}/friends/{friendId}")
    public void deleteFriend(@PathVariable long id, @PathVariable long friendId) {
        log.info(LoggingMessages.DELETE_FRIEND.toString(), id, friendId);
        service.removeFriend(id, friendId);
    }

    /**
     * Get list of friends for user with ID
     * @param id user ID
     * @return list of friends
     */
    @GetMapping("{id}/friends")
    public List<User> getFriends(@PathVariable long id) {
        log.info(LoggingMessages.GET_FRIENDS.toString(), id);
        List<User> list = service.getFriends(id);
        return list;
    }

    /**
     * Get common friends for users with IDs
     * @param id user ID
     * @param otherId other user ID
     * @return list of common friends
     */
    @GetMapping("{id}/friends/common/{otherId}")
    public List<User> getCommonFriends(@PathVariable long id, @PathVariable long otherId) {
        log.info(LoggingMessages.GET_COMMON_FRIENDS.toString(), id, otherId);
        return service.getCommonFriends(id, otherId);
    }
}
