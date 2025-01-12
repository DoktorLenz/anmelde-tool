package dev.stinner.scoutventure.application.rest.models;

import dev.stinner.scoutventure.application.rest.security.OAuth2FrontendConfiguration;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class OAuth2FrontendConfigurationDtoTest {

    @Test
    @DisplayName("OAuth2ConfigurationDto should be created from OAuth2Configuration")
    void testStaticOf() {
        OAuth2FrontendConfiguration configuration = new OAuth2FrontendConfiguration();
        configuration.setClientId("clientId");
        configuration.setRealm("scoutventure");
        configuration.setServerUrl("http://keycloak.io");

        OAuth2FrontendConfigurationDto dto = OAuth2FrontendConfigurationDto.of(configuration);

        assertThat(dto.getAuthority()).isEqualTo("http://keycloak.io/realms/scoutventure");
        assertThat(dto.getClientId()).isEqualTo(configuration.getClientId());
    }
}
