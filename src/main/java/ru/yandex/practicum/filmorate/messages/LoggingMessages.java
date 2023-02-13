package ru.yandex.practicum.filmorate.messages;

/**
 * Messages for logger
 * @author Evgeniy Lee
 */
public enum LoggingMessages {
    GET("Getting all data"),
    CREATE("Creating data {}"),
    UPDATE("Updating data {}"),
    NO_FOUND("Data {} not found"),
    SUCCESSFUL_CREATE("Data {} successfully created"),
    SUCCESSFUL_UPDATE("Data {} successfully updated"),
    GET_ALL_DATA("List of all data {}");

    private String message;

    private LoggingMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

