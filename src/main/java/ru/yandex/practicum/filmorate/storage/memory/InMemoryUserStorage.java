package ru.yandex.practicum.filmorate.storage.memory;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DataStorage;
import ru.yandex.practicum.filmorate.storage.UserStorage;

/**
 * User storage in memory
 * @author Evgeniy Lee
 */
@Component("inMemoryUserStorage")
public class InMemoryUserStorage extends DataStorage<User> implements UserStorage {
}
