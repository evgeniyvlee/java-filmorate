package ru.yandex.practicum.filmorate.model;

import lombok.EqualsAndHashCode;

/**
 * Data type contains only ID field
 * @author Evgeniy Lee
 */
@lombok.Data
public abstract class Data {
    // ID
    @EqualsAndHashCode.Exclude
    protected Long id;
}
