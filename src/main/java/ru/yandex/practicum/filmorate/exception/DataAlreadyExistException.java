package ru.yandex.practicum.filmorate.exception;

/**
 * The DataAlreadyExistException is general class of exceptions produced by create methods for user and film storage.
 * @author Evgeniy Lee
 */
public class DataAlreadyExistException extends Exception {
    /**
     * Constructor
     * @param message exception message
     */
    public DataAlreadyExistException(String message) {
        super(message);
    }
}
