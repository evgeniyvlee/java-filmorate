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
    DELETE_DATA_BY_ID("Data with id={} was deleted"),
    GET_BY_ID("Getting data with id={}"),
    DELETE_BY_ID("Deleting data with id={}"),
    ADD_LIKE("Add like user with id={} by other user with userId={}"),
    DELETE_LIKE("Delete like user with id={} by other user with userId={}"),
    GET_POPULAR_FILMS("Getting {} popular films"),
    ADD_FRIEND("User with id={} add as a friend other user with friendId={}"),
    DELETE_FRIEND("User with id={} remove as a friend other user with friendId={}"),
    GET_FRIENDS("Getting user friends with id={}"),
    GET_COMMON_FRIENDS("Getting user with id={} and other user with otherId={} common friends");

    private String message;

    private LoggingMessages(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return this.message;
    }
}

