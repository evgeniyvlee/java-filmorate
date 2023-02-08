package ru.yandex.practicum.filmorate.model;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import java.time.LocalDate;

/**
 * Film type
 * @author Evgeniy Lee
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class Film extends Data {
    // Film name
    private String name;
    // Film description
    private String description;
    // Film release data
    private LocalDate releaseDate;
    // Film duration in minutes
    private long duration;
}
