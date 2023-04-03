package de.stinner.anmeldetool.domain.auth.api.model;

import de.stinner.anmeldetool.application.constraints.Password;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import jakarta.validation.constraints.NotNull;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class FinishRegistrationDtoTest {
    @Test
    @DisplayName("FinishRegistrationDto should have working getters and setters")
    void testGettersAndSetters() {
        FinishRegistrationDto dto = new FinishRegistrationDto();

        UUID registrationId = UUID.randomUUID();
        String password = "password";

        dto.setRegistrationId(registrationId);
        dto.setPassword(password);

        assertThat(dto.getRegistrationId()).isEqualTo(registrationId);
        assertThat(dto.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("FinishRegistrationDto should have @NotNull and @Password constraints")
    @SneakyThrows
    void testConstraints() {
        assertThat(FinishRegistrationDto.class.getDeclaredField("registrationId").getAnnotation(NotNull.class))
                .isNotNull();
        assertThat(FinishRegistrationDto.class.getDeclaredField("password").getAnnotation(Password.class))
                .isNotNull();
    }

    @Test
    @DisplayName("FinishRegistrationDto should validate UUID and password lengths")
    void testValidation() {
        FinishRegistrationDto dto = new FinishRegistrationDto();
        dto.setRegistrationId(null);
        dto.setPassword("shortpw");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<FinishRegistrationDto>> violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(2)
                .anySatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath()).hasToString("registrationId");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(NotNull.class);
                })
                .anySatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath()).hasToString("password");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
                });

        dto.setRegistrationId(UUID.randomUUID());
        dto.setPassword("thispasswordistoolongtobevalidbecauseitexceedsfifty-sixcharacters");

        violations = validator.validate(dto);
        assertThat(violations)
                .hasSize(1)
                .allSatisfy(violation -> {
                    assertThat(violation.getRootBean()).isEqualTo(dto);
                    assertThat(violation.getPropertyPath()).hasToString("password");
                    assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
                });

        dto.setPassword("validpassword");

        violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
}
