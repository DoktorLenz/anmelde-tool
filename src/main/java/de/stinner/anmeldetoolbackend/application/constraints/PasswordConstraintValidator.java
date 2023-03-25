package de.stinner.anmeldetoolbackend.application.constraints;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordConstraintValidator implements ConstraintValidator<Password, String> {

    @Override
    public void initialize(final Password constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        // Min & Max are defined by BCrypt
        if (password.length() < 8) {
            context
                    .buildConstraintViolationWithTemplate("Password must be at least 8 characters.")
                    .addConstraintViolation();
        } else if (password.length() > 56) {
            context
                    .buildConstraintViolationWithTemplate("Password must be at most 56 characters.")
                    .addConstraintViolation();
        } else {
            return true;
        }
        context.disableDefaultConstraintViolation();
        return false;
    }
}
