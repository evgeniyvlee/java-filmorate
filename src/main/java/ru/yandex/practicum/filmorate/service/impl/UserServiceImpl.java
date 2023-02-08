package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.ModelService;

/**
 * User service provides operations of creating, updating and getting for user data
 * @author Evgeniy Lee
 */
@Component
public class UserServiceImpl extends ModelService<User> {

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
}
