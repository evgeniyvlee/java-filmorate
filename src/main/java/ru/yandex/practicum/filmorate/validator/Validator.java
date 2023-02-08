package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Data;

/**
 * Validator interface provides validate contract
 * @param <T> data type
 * @author Evgeniy Lee
 */
public interface Validator<T extends Data> {
    /**
     * Validate data
     * @param data data which validator checks
     * @throws ValidationException if validate error occurs
     */
    void validate(T data) throws ValidationException;
}
