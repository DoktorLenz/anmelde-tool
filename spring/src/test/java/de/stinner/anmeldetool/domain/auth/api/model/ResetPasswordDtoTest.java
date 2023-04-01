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

public class ResetPasswordDtoTest {

    @Test
    @DisplayName("ResetPasswordDto should have working getters and setters")
    void testGettersAndSetters() {
        ResetPasswordDto dto = new ResetPasswordDto();

        UUID resetId = UUID.randomUUID();
        String password = "password";

        dto.setResetId(resetId);
        dto.setPassword(password);

        assertThat(dto.getResetId()).isEqualTo(resetId);
        assertThat(dto.getPassword()).isEqualTo(password);
    }

    @Test
    @DisplayName("ResetPasswordDto should have @NotNull and @Password constraints")
    @SneakyThrows
    void testConstraints() {
        assertThat(ResetPasswordDto.class.getDeclaredField("resetId").getAnnotation(NotNull.class))
                .isNotNull();
        assertThat(ResetPasswordDto.class.getDeclaredField("password").getAnnotation(Password.class))
                .isNotNull();
    }

    @Test
    @DisplayName("FinishRegistrationDto should validate UUID and password lengths")
    void testValidation() {
        ResetPasswordDto dto = new ResetPasswordDto();
        dto.setResetId(null);
        dto.setPassword("shortpw");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ResetPasswordDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2);

        assertThat(violations).anySatisfy(violation -> {
            assertThat(violation.getRootBean()).isEqualTo(dto);
            assertThat(violation.getPropertyPath().toString()).isEqualTo("resetId");
            assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(NotNull.class);
        });

        assertThat(violations).anySatisfy(violation -> {
            assertThat(violation.getRootBean()).isEqualTo(dto);
            assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
            assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
        });

        dto.setResetId(UUID.randomUUID());
        dto.setPassword("thispasswordistoolongtobevalidbecauseitexceedsfifty-sixcharacters");

        violations = validator.validate(dto);
        assertThat(violations).hasSize(1);
        assertThat(violations).allSatisfy(violation -> {
            assertThat(violation.getRootBean()).isEqualTo(dto);
            assertThat(violation.getPropertyPath().toString()).isEqualTo("password");
            assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
        });

        dto.setPassword("validpassword");

        violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }
}
