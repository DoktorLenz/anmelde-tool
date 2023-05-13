package de.stinner.anmeldetool.domain.auth.persistence;

import de.stinner.anmeldetool.domain.auth.api.model.RegistrationRequestDto;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class RegistrationEntityTest {

    @Test
    void shouldConvertRegistrationRequestToEntity() {
        // Arrange
        RegistrationRequestDto dto = new RegistrationRequestDto();
        dto.setFirstname("firstname");
        dto.setLastname("lastname");
        dto.setEmail("validmail@localhost");

        // Act
        RegistrationEntity entity = RegistrationEntity.of(dto);

        // Assert
        assertThat(entity.getRegistrationId()).isNull();
        assertThat(entity.getFirstname()).isEqualTo(dto.getFirstname());
        assertThat(entity.getLastname()).isEqualTo(dto.getLastname());
        assertThat(entity.getEmail()).isEqualTo(dto.getEmail());
        assertThat(entity.getCreatedAt()).isNull();
        assertThat(entity.getEmailSent()).isNull();
    }
}
