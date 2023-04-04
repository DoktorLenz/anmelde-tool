package de.stinner.anmeldetool.domain.auth.api.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ForgotPasswordRequestDtoTest {

    @Test
    @DisplayName("ForgotPasswordRequestDto should have working getters and setters")
    void testGettersAndSetters() {
        ForgotPasswordRequestDto dto = new ForgotPasswordRequestDto();

        String email = "valid-email@example.com";

        dto.setEmail(email);

        assertThat(dto.getEmail()).isEqualTo(email);
    }

    @Test
    @DisplayName("ForgotPasswordRequestDto should have NotBlank and Email annotations on email field")
    void testAnnotations() throws Exception {
        Field emailField = ForgotPasswordRequestDto.class.getDeclaredField("email");

        Annotation[] annotations = emailField.getAnnotations();

        assertThat(annotations)
                .hasSize(2)
                .anySatisfy(annotation -> {
                    assertThat(annotation.annotationType()).isEqualTo(NotBlank.class);
                })
                .anySatisfy(annotation -> {
                    assertThat(annotation.annotationType()).isEqualTo(Email.class);
                });
    }

    @Test
    @DisplayName("ForgotPasswordRequestDto should validate email format")
    void testValidation() {
        ForgotPasswordRequestDto dto = new ForgotPasswordRequestDto();
        dto.setEmail("");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ForgotPasswordRequestDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath()).hasToString("email");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(NotBlank.class);
                });

        dto.setEmail("invalid-email");

        violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .anySatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath()).hasToString("email");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Email.class);
                });

        dto.setEmail("valid-email@example.com");

        violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
}
