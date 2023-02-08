package ru.yandex.practicum.filmorate.exception;

/**
 * The ValidationException is general class of exceptions produced by validation methods for User and Film.
 * @author Evgeniy Lee
 */
public class ValidationException extends Exception {
    /**
     * Constructor
     * @param message exception message
     */
    public ValidationException(String message) {
        super(message);
    }
}
