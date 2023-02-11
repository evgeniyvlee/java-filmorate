package ru.yandex.practicum.filmorate.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class ReleaseValidator implements ConstraintValidator<Release, LocalDate> {

    private final static LocalDate RELEASE_DATE_AFTER = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate localDate, ConstraintValidatorContext constraintValidatorContext) {
        if (localDate == null) {
            return false;
        }
        return localDate.isAfter(RELEASE_DATE_AFTER);
    }
}
