package de.stinner.anmeldetool.domain.auth.persistence;

import de.stinner.anmeldetool.application.auth.WebSecurityConfiguration;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UserDataEntityTest {
    @BeforeAll
    static void setupPasswordEncoder() {
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        Mockito.mockStatic(WebSecurityConfiguration.class)
                .when(WebSecurityConfiguration::encoder)
                .thenReturn(encoder);
    }

    @Test
    void createUserFromRegistration() {
        RegistrationEntity registrationEntity = new RegistrationEntity();
        registrationEntity.setRegistrationId(UUID.randomUUID());
        registrationEntity.setFirstname("Firstname");
        registrationEntity.setLastname("Lastname");
        registrationEntity.setEmail("validmail@localhost");
        registrationEntity.setCreatedAt(Instant.now());
        registrationEntity.setEmailSent(Instant.now());

        String password = "thisisapassword";

        UserDataEntity userDataEntity = UserDataEntity.create(registrationEntity, password);

        assertThat(userDataEntity.getId()).isNull();
        assertThat(userDataEntity.getEmail()).isEqualTo(registrationEntity.getEmail());
        assertThat(userDataEntity.getPassword()).isNotEqualTo(password);
        assertThat(WebSecurityConfiguration.encoder().matches(password, userDataEntity.getPassword())).isTrue();
        assertThat(userDataEntity.isAccountLocked()).isFalse();
        assertThat(userDataEntity.isCredentialsExpired()).isFalse();
        assertThat(userDataEntity.isEnabled()).isTrue();
        assertThat(userDataEntity.getAuthorities()).isNotNull().hasSize(0);
        assertThat(userDataEntity.getFirstname()).isEqualTo(registrationEntity.getFirstname());
        assertThat(userDataEntity.getLastname()).isEqualTo(registrationEntity.getLastname());
        assertThat(userDataEntity.getCreatedAt()).isNull();
    }

    @Test
    void resetCredetialsExpiredBySettingNewPassword() {
        // Arrange
        UserDataEntity entity = new UserDataEntity();
        entity.setCredentialsExpired(true);
        String newPassword = "somenewpassword";

        // Act
        entity.setPassword(newPassword);

        // Assert
        assertThat(entity.isCredentialsExpired()).isFalse();
        assertThat(WebSecurityConfiguration.encoder().matches(newPassword, entity.getPassword())).isTrue();
    }

    @Test
    void changePasswordShouldThrowAnErrorIfOldPasswordDoesNotMatch() {
        // Arrange
        UserDataEntity entity = new UserDataEntity();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        entity.setPassword(oldPassword);

        // Act & Assert
        assertThatThrownBy(() -> entity.changePassword("nottheoldpassword", newPassword))
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining("Old password is wrong.");
    }

    @Test
    void changePasswordWithRightPassword() {
        // Arrange
        UserDataEntity entity = new UserDataEntity();
        String oldPassword = "oldPassword";
        String newPassword = "newPassword";
        entity.setPassword(oldPassword);

        // Act
        entity.changePassword(oldPassword, newPassword);

        // Assert
        assertThat(entity.getPassword()).isNotEqualTo(oldPassword).isNotEqualTo(newPassword);
        assertThat(WebSecurityConfiguration.encoder().matches(oldPassword, entity.getPassword())).isFalse();
        assertThat(WebSecurityConfiguration.encoder().matches(newPassword, entity.getPassword())).isTrue();
    }
}
