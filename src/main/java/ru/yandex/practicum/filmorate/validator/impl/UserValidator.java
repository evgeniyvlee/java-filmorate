package ru.yandex.practicum.filmorate.validator.impl;

import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.validator.Validator;
import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * User data validator
 * @author Evgeniy Lee
 */
@Component
public class UserValidator implements Validator<User> {
    // Email regular expression
    private final static String EMAIL_PATTERN = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";

    /**
     * {@inheritDoc}
     */
    @Override
    public void validate(final User user) throws ValidationException {

        if (user == null) {
            throw new ValidationException("User is NULL");
        }
        // Check email is valid
        String email = user.getEmail();
        if (email == null) {
            throw new ValidationException("Email is NULL");
        } else if (email.isBlank()) {
            throw new ValidationException("Email is blank");
        } else if (!Pattern.matches(EMAIL_PATTERN, email)) {
            throw new ValidationException("Invalid email format");
        }
        // Check login is valid
        String login = user.getLogin();
        if (login == null) {
            throw new ValidationException("Login is NULL");
        } else if (login.isBlank()) {
            throw new ValidationException("Login is blank");
        } else if (login.contains(" ")) {
            throw new ValidationException("Login contains spaces");
        }
        // Check birthday is valid
        LocalDate birthday = user.getBirthday();
        if (birthday == null) {
            throw new ValidationException("Birthday is NULL");
        } else if (birthday.isAfter(LocalDate.now())) {
            throw new ValidationException("Birthday in future");
        }
    }
}
