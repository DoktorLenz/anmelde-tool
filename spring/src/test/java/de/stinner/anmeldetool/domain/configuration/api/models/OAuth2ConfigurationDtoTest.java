package de.stinner.anmeldetool.domain.configuration.api.models;

import de.stinner.anmeldetool.domain.configuration.api.OAuth2Configuration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2ConfigurationDtoTest {

    @Test
    @DisplayName("OAuth2ConfigurationDto should be created from OAuth2Configuration")
    void testStaticOf() {
        OAuth2Configuration configuration = new OAuth2Configuration();
        configuration.setClientId("clientId");
        configuration.setIssuerUri("issuerUri");
        configuration.setSecureRoutes(List.of("route1", "route2"));

        OAuth2ConfigurationDto dto = OAuth2ConfigurationDto.of(configuration);

        assertThat(dto.getAuthority()).isEqualTo(configuration.getIssuerUri());
        assertThat(dto.getClientId()).isEqualTo(configuration.getClientId());
        assertThat(dto.getSecureRoutes()).containsExactlyElementsOf(configuration.getSecureRoutes());
    }
}
