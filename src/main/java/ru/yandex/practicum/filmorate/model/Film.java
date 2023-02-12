package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.exception.ExceptionMessages;
import ru.yandex.practicum.filmorate.validation.constraints.Release;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

/**
 * Film type
 * @author Evgeniy Lee
 */
@lombok.Data
public class Film extends Data {
    // Film name
    @NotBlank(message = ExceptionMessages.FILM_NAME_BLANK)
    private String name;
    // Film description
    @Size(max = 200, message = ExceptionMessages.FILM_DESCRIPTION_TOO_LONG)
    private String description;
    // Film release data
    @NotNull(message = ExceptionMessages.FILM_RELEASE_DATE_NULL)
    @Release(message = ExceptionMessages.FILM_RELEASE_DATE_TOO_EARLY)
    private LocalDate releaseDate;
    // Film duration in minutes
    @PositiveOrZero(message = ExceptionMessages.FILM_DURATION_NEGATIVE)
    private long duration;
}
