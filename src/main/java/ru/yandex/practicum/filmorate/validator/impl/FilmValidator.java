package ru.yandex.practicum.filmorate.validator.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.validator.Validator;
import java.time.LocalDate;

/**
 * Film data validator
 * @author Evgeniy Lee
 */
@Component
public class FilmValidator implements Validator<Film> {
    // Max length of description
    private final static int MAX_DESCRIPTION_LENGTH = 200;

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(Film film) throws ValidationException {

        if (film == null) {
            throw new ValidationException("Film is NULL");
        }
        // Check name is valid
        String name = film.getName();
        if (name == null) {
            throw new ValidationException("Film name is NULL");
        } else if (name.isBlank()) {
            throw new ValidationException("Film name is blank");
        }
        // Check description is valid
        String description = film.getDescription();
        if ((description != null) && (description.length() >= MAX_DESCRIPTION_LENGTH)) {
            throw new ValidationException("Description length more than 200 symbols");
        }
        // Check release date is valid
        LocalDate releaseDate = film.getReleaseDate();
        if ((releaseDate != null) && (releaseDate.isBefore(LocalDate.of(1895, 12, 28)))) {
            throw new ValidationException("Release date is earlier 1895-12-28");
        }
        // Check duration is valid
        if (film.getDuration() <= 0) {
            throw new ValidationException("Duration is negative");
        }
    }
}
