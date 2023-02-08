package ru.yandex.practicum.filmorate.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.time.LocalDate;

/**
 * User type
 * @author Evgeniy Lee
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class User extends Data {
    // User email
    private String email;
    // User login
    private String login;
    // User name
    private String name;
    // User birthday
    private LocalDate birthday;
}
