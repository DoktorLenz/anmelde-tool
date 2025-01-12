package dev.stinner.scoutventure.infrastructure.iam;

import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class IamConfigurationTest {

    @Test
    @DisplayName("IamConfiguration should return KeycloakAdapter if provider is set to keycloak")
    void testProviderKeycloak() {
        IamConfiguration iamConfiguration = new IamConfiguration();
        KeycloakAdapter keycloakAdapter = new KeycloakAdapter(iamConfiguration);
        iamConfiguration.setProvider("keycloak");
        assertThat(iamConfiguration.iamAdapterImpl(keycloakAdapter)).isEqualTo(keycloakAdapter);
    }

    @Test
    @DisplayName("IamConfiguration should throw error on invalid oauth provider")
    @SneakyThrows
    void testProviderInvalid() {
        IamConfiguration iamConfiguration = new IamConfiguration();
        KeycloakAdapter keycloakAdapter = new KeycloakAdapter(iamConfiguration);
        iamConfiguration.setProvider("invalid");
        assertThatThrownBy(() -> iamConfiguration.iamAdapterImpl(keycloakAdapter))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("invalid");
    }
}
