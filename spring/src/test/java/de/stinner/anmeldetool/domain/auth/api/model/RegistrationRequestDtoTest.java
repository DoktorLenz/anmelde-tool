package de.stinner.anmeldetool.domain.auth.api.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationRequestDtoTest {
    @Test
    @DisplayName("RegistrationRequestDto should have working getters and setters")
    void testGettersAndSetters() {
        RegistrationRequestDto dto = new RegistrationRequestDto();

        String email = "valid-email@example.com";
        String firstname = "FirstName";
        String lastname = "LastName";

        dto.setEmail(email);
        dto.setFirstname(firstname);
        dto.setLastname(lastname);

        assertThat(dto.getEmail()).isEqualTo(email);
        assertThat(dto.getFirstname()).isEqualTo(firstname);
        assertThat(dto.getLastname()).isEqualTo(lastname);

    }

    @Test
    @DisplayName("RegistrationRequestDto should have @NotNull and @Email constraints")
    @SneakyThrows
    void testConstraints() {
        assertThat(RegistrationRequestDto.class.getDeclaredField("email").getAnnotation(NotBlank.class))
                .isNotNull();
        assertThat(RegistrationRequestDto.class.getDeclaredField("email").getAnnotation(Email.class))
                .isNotNull();

        assertThat(RegistrationRequestDto.class.getDeclaredField("firstname").getAnnotation(NotBlank.class))
                .isNotNull();
        assertThat(RegistrationRequestDto.class.getDeclaredField("lastname").getAnnotation(NotBlank.class))
                .isNotNull();
    }

    @Test
    @DisplayName("RegistrationRequestDto should validate firstname, lastname and email")
    void testValidation() {
        RegistrationRequestDto dto = new RegistrationRequestDto();
        dto.setEmail(null);
        dto.setFirstname(null);
        dto.setLastname(null);

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<RegistrationRequestDto>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(3)
                .allSatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath().toString()).isIn("email", "firstname", "lastname");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(NotBlank.class);
                });

        dto.setEmail("invalid-email");
        dto.setFirstname("firstname");
        dto.setLastname("lastname");

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
