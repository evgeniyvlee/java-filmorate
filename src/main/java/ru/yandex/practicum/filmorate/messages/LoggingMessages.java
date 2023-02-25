package ru.yandex.practicum.filmorate.messages;

/**
 * Messages for logger
 * @author Evgeniy Lee
 */
public enum LoggingMessages {
    GET("Getting all data"),
    CREATE("Creating data {}"),
    UPDATE("Updating data {}"),
    DATA_NOT_FOUND("Data {} not found"),
    ID_NOT_FOUND("Data with id={} not found"),
    SUCCESSFUL_CREATE("Data {} successfully created"),
    SUCCESSFUL_UPDATE("Data {} successfully updated"),
    GET_ALL_DATA("List of all data {}"),
    GET_DATA_BY_ID("Data {} successfully found"),
    DATA_ALREADY_EXIST("Data {} is already exist"),
    DELETE_DATA_BY_ID("Data with id={} was deleted");

    private String message;

    private LoggingMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

