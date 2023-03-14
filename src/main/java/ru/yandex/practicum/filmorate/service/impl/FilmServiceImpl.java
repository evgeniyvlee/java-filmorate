package ru.yandex.practicum.filmorate.service.impl;

import org.springframework.beans.factory.annotation.Qualifier;
import ru.yandex.practicum.filmorate.dao.FilmDao;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.service.AbstractService;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.Storage;
import java.util.List;

/**
 * User service provides operations of creating, updating and getting for film data
 * @author Evgeniy Lee
 */
@org.springframework.stereotype.Service
public class FilmServiceImpl extends AbstractService<Film> implements FilmService {
    // User storage
    private final Storage<User> userStorage;
    //
    private final FilmDao filmDao;

    /**
     * Constructor
     * @param filmStorage film storage
     * @param userStorage user storage
     */
    public FilmServiceImpl(
            @Qualifier("dbFilmStorage") final Storage<Film> filmStorage,
            @Qualifier("dbUserStorage") final Storage<User> userStorage,
            final FilmDao filmDao
    ) {
        super(filmStorage);
        this.userStorage = userStorage;
        this.filmDao = filmDao;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addLike(final long id, final long userId) {
        final Film film = super.get(id);
        final User user = userStorage.get(userId);
        filmDao.addLike(film.getId(), user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeLike(final long id, final long userId) {
        final Film film = super.get(id);
        final User user = userStorage.get(userId);
        filmDao.removeLike(film.getId(), user.getId());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Film> getPopular(final int count) {
        return filmDao.getPopular(count);
    }
}
