package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.validation.constraints.Release;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.constraints.Positive;
import java.time.LocalDate;

/**
 * Film type
 * @author Evgeniy Lee
 */
@lombok.Data
public class Film extends Data {
    // Film name
    @NotBlank(message = "Film name is blank")
    private String name;
    // Film description
    @Size(max = 200, message = "Description length more than 200 symbols")
    private String description;
    // Film release data
    @NotNull(message = "Film release date is NULL")
    @Release(message = "Film release date is earlier 1895-12-28")
    private LocalDate releaseDate;
    // Film duration in minutes
    @Positive(message = "Film duration is negative")
    private long duration;
}
