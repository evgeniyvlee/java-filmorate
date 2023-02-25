package ru.yandex.practicum.filmorate.service;

import ru.yandex.practicum.filmorate.exception.DataNotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

/**
 * FilmService interface provides additional functions for films
 * @author Evgeniy Lee
 */
public interface FilmService extends Service<Film> {
    /**
     * Add like to film
     * @param id film ID
     * @param userId user ID who sent like
     * @throws DataNotFoundException occurs if film or user not found
     */
    void addLike(long id, long userId) throws DataNotFoundException;

    /**
     * Remove like from film
     * @param id film ID
     * @param userId user ID who sent like
     * @throws DataNotFoundException occurs if film or user not found
     */
    void removeLike(long id, long userId) throws DataNotFoundException;

    /**
     * Get list of popular films
     * @param count popular film count
     * @return list of popular films
     */
    List<Film> getPopular(int count);
}
