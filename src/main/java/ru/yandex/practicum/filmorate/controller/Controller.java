package ru.yandex.practicum.filmorate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Data;
import ru.yandex.practicum.filmorate.service.ModelService;
import java.util.List;

/**
 * Abstract controller provides methods for creating, updating and getting data from storage.
 * @param <T> data type
 * @author Evgeniy Lee
 */
public abstract class Controller<T extends Data> {
    // Service for creating, updating and getting data
    @Autowired
    private ModelService<T> service;

    /**
     * Get all data from storage
     * @return list of data
     */
    public List<T> getAll() {
        return service.getAll();
    }

    /**
     * Create new data instance in storage
     * @param data data instance
     * @return created data
     */
    public T create(final T data) {
        return service.create(data);
    }

    /**
     * Update data instance in storage
     * @param data data instance
     * @return updated data
     */
    public T update(final T data) throws ValidationException {
        return service.update(data);
    }
}
