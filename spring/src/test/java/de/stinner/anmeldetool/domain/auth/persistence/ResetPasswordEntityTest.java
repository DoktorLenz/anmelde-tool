package de.stinner.anmeldetool.domain.auth.persistence;

import de.stinner.anmeldetool.domain.auth.service.Authority;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

class ResetPasswordEntityTest {

    @Test
    void shouldConvertUserDataEntityToResetPasswordEntity() {
        // Arrange
        UserDataEntity userDataEntity = new UserDataEntity();
        userDataEntity.setId(UUID.randomUUID());
        userDataEntity.setEmail("validmail@localhost");
        userDataEntity.setPassword("validpassword");
        userDataEntity.setAccountLocked(false);
        userDataEntity.setCredentialsExpired(false);
        userDataEntity.setEnabled(true);
        userDataEntity.setAuthorities(new Authority[]{Authority.ROLE_USER});
        userDataEntity.setCreatedAt(Instant.now());
        userDataEntity.setFirstname("firstname");
        userDataEntity.setLastname("lastname");

        // Act
        ResetPasswordEntity resetPasswordEntity = ResetPasswordEntity.of(userDataEntity);

        // Assert
        assertThat(resetPasswordEntity.getUser()).isSameAs(userDataEntity);
        assertThat(resetPasswordEntity.getResetId()).isNull();
        assertThat(resetPasswordEntity.getCreatedAt()).isNull();
        assertThat(resetPasswordEntity.getEmailSent()).isNull();
    }
}
