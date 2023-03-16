package ru.yandex.practicum.filmorate.dao;

import ru.yandex.practicum.filmorate.model.Film;
import java.util.List;

/**
 * Film DAO interface provides methods for films.
 * @author Evgeniy Lee
 */
public interface FilmDao {
    /**
     * Add like to film
     * @param id film ID
     * @param userId user ID who likes film
     */
    void addLike(long id, long userId);

    /**
     * Remove like film
     * @param id film ID
     * @param userId user ID who removes like
     */
    void removeLike(long id, long userId);

    /**
     * Get count popular films
     * @param count film count
     * @return list of popular films
     */
    List<Film> getPopular(int count);
}
