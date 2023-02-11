package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.*;
import java.time.LocalDate;

/**
 * User type
 * @author Evgeniy Lee
 */
@lombok.Data
public class User extends Data {
    // User email
    @NotBlank(message = "User email is blank")
    @Email(message = "Incorrect email")
    private String email;
    // User login
    @NotBlank(message = "User login is blank")
    @Pattern(regexp = "^[^<\\s>]+$", message = "User login contains spaces")
    private String login;
    // User name
    private String name;
    // User birthday
    @NotNull(message = "User birthday is NULL")
    @Past(message = "User birthday in future")
    private LocalDate birthday;
}
