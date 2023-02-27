package ru.yandex.practicum.filmorate.controller;

import ru.yandex.practicum.filmorate.model.Data;
import java.util.List;

/**
 * Abstract controller provides methods for creating, updating and getting data from storage.
 * @param <T> data type
 * @author Evgeniy Lee
 */
public interface Controller<T extends Data> {
    /**
     * Get all data from storage
     * @return list of data
     */
    List<T> getAll();

    /**
     * Create new data instance in storage
     * @param data data instance
     * @return created data
     */
    T create(T data);

    /**
     * Update data instance in storage
     * @param data data instance
     * @return updated data
     */
    T update(T data);

    /**
     * Get data instance from storage by ID
     * @param id data ID
     * @return found data
     */
    T get(long id);

    /**
     * Delete data instance from storage by id
     * @param id data ID
     */
    void delete(long id);
}
