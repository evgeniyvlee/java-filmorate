package ru.yandex.practicum.filmorate.model;

import ru.yandex.practicum.filmorate.messages.ExceptionMessages;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import java.time.LocalDate;

/**
 * User type
 * @author Evgeniy Lee
 */
@lombok.Data
public class User extends Data {
    // User email
    @NotBlank(message = ExceptionMessages.USER_EMAIL_BLANK)
    @Email(message = ExceptionMessages.USER_EMAIL_INCORRECT)
    private String email;
    // User login
    @NotBlank(message = ExceptionMessages.USER_LOGIN_BLANK)
    @Pattern(regexp = "^[^<\\s>]+$", message = ExceptionMessages.USER_LOGIN_CONTAINS_SPACES)
    private String login;
    // User name
    private String name;
    // User birthday
    @NotNull(message = ExceptionMessages.USER_BIRTHDAY_NULL)
    @Past(message = ExceptionMessages.USER_BIRTHDAY_IN_FUTURE)
    private LocalDate birthday;
}
