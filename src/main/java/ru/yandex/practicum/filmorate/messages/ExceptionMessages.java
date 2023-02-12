package ru.yandex.practicum.filmorate.messages;

public interface ExceptionMessages {
    String FILM_NAME_BLANK = "Film name is blank";
    String FILM_DESCRIPTION_TOO_LONG = "Description length more than 200 symbols";
    String FILM_RELEASE_DATE_NULL = "Film release date is NULL";
    String FILM_RELEASE_DATE_TOO_EARLY = "Film release date is earlier 1895-12-28";
    String FILM_DURATION_NEGATIVE = "Film duration is negative";
    String USER_EMAIL_BLANK = "User email is blank";
    String USER_EMAIL_INCORRECT = "Incorrect email";
    String USER_LOGIN_BLANK = "User login is blank";
    String USER_LOGIN_CONTAINS_SPACES = "User login contains spaces";
    String USER_BIRTHDAY_NULL = "User birthday is NULL";
    String USER_BIRTHDAY_IN_FUTURE = "User birthday in future";
}
