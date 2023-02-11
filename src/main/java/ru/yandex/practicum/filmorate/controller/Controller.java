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
     * @throws ValidationException if validation error occurs
     */
    public T create(final T data) throws ValidationException {
        return service.create(data);
    }

    /**
     * Update data instance in storage
     * @param data data instance
     * @return updated data
     * @throws ValidationException if validation error occurs
     */
    public T update(final T data) throws ValidationException {
        T updatedData = service.update(data);
        if (updatedData == null) {
            throw new ValidationException("User not found");
        }
        return updatedData;
    }

    protected enum Messages {
        GET("Getting all data"),
        CREATE("Creating data {}"),
        UPDATE("Updating data {}");

        private String message;

        private Messages(String message) {
            this.message = message;
        }

        @Override
        public String toString() {
            return this.message;
        }
    }
}
