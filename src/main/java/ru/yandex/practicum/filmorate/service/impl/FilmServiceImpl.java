package ru.yandex.practicum.filmorate.service.impl;

import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * User service provides operations of creating, updating and getting for film data
 * @author Evgeniy Lee
 */
@org.springframework.stereotype.Service
public class FilmServiceImpl extends AbstractService<Film> implements FilmService {
    // User storage
    private final Storage<User> userStorage;
    // Comparator for getting popular films
    private static final Comparator<Film> FILM_COMPARATOR = Comparator.comparingLong((film) -> film.getUserIds().size());

    /**
     * Constructor
     * @param filmStorage film storage
     * @param userStorage user storage
     */
    public FilmServiceImpl(final Storage<Film> filmStorage, final Storage<User> userStorage) {
        super(filmStorage);
        this.userStorage = userStorage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLike(final long id, final long userId) {
        final Film film = super.get(id);
        final User user = userStorage.get(userId);
        film.getUserIds().add(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLike(final long id, final long userId) {
        final Film film = super.get(id);
        final User user = userStorage.get(userId);
        film.getUserIds().remove(userId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Film> getPopular(final int count) {
        List<Film> list = storage.getAll().stream()
                .sorted(FILM_COMPARATOR.reversed())
                .limit(count)
                .collect(Collectors.toList());
        return list;
    }
}
