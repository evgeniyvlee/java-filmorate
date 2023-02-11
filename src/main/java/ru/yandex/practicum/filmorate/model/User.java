package ru.yandex.practicum.filmorate.model;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * User type
 * @author Evgeniy Lee
 */
@lombok.Data
public class User extends Data {
    // User email
    @Email(message = "Incorrect email")
    private String email;
    // User login
    @NotBlank(message = "User login is blank")
    private String login;
    // User name
    private String name;
    // User birthday
    @Past(message = "User birthday in future")
    private LocalDate birthday;
}
