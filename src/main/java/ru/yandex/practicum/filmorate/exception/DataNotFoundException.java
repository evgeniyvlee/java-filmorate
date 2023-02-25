package ru.yandex.practicum.filmorate.exception;

/**
 * The DataNotFoundException is general class of exceptions produced by get and update methods for user and film storage.
 * @author Evgeniy Lee
 */
public class DataNotFoundException extends Exception {
    /**
     * Constructor
     * @param message exception message
     */
    public DataNotFoundException(String message) {
        super(message);
    }
}
