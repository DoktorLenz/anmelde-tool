package de.stinner.anmeldetool.domain.auth.api.model;

import static org.assertj.core.api.Assertions.assertThat;

import de.stinner.anmeldetool.application.constraints.Password;
import jakarta.validation.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ChangePasswordDtoTest {

    @Test
    @DisplayName("ChangePasswordDto should have working getters and setters")
    void testGettersAndSetters() {
        ChangePasswordDto dto = new ChangePasswordDto();

        String oldPassword = "oldPassword";
        String newPassword = "newPassword";

        dto.setOldPassword(oldPassword);
        dto.setNewPassword(newPassword);

        assertThat(dto.getOldPassword()).isEqualTo(oldPassword);
        assertThat(dto.getNewPassword()).isEqualTo(newPassword);
    }

    @Test
    @DisplayName("ChangePasswordDto should have @Password constraint")
    void testPasswordConstraint() {
        assertThat(ChangePasswordDto.class.getDeclaredFields())
                .extracting(field -> field.getAnnotation(Password.class))
                .allSatisfy(annotation -> assertThat(annotation).isNotNull());
    }

    @Test
    @DisplayName("ChangePasswordDto should validate password lengths")
    void testPasswordLength() {
        ChangePasswordDto dto = new ChangePasswordDto();
        dto.setOldPassword("shortpw");
        dto.setNewPassword("thispasswordistoolongtobevalidbecauseitexceedsfifty-sixcharacters");

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();

        Set<ConstraintViolation<ChangePasswordDto>> violations = validator.validate(dto);
        assertThat(violations).hasSize(2);

        assertThat(violations).anySatisfy(violation -> {
            assertThat(violation.getRootBean()).isEqualTo(dto);
            assertThat(violation.getPropertyPath().toString()).isEqualTo("oldPassword");
            assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
        });

        assertThat(violations).anySatisfy(violation -> {
            assertThat(violation.getRootBean()).isEqualTo(dto);
            assertThat(violation.getPropertyPath().toString()).isEqualTo("newPassword");
            assertThat(violation.getConstraintDescriptor().getAnnotation()).isInstanceOf(Password.class);
        });

        dto.setOldPassword("longenoughpassword");
        dto.setNewPassword("validpw123");

        violations = validator.validate(dto);
        assertThat(violations).isEmpty();
    }


}