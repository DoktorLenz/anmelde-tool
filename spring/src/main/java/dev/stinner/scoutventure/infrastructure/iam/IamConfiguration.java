package dev.stinner.scoutventure.infrastructure.iam;

import dev.stinner.scoutventure.domain.ports.spi.IamAdapter;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Data
@Configuration
public class IamConfiguration {
    @Value("${scoutventure.oauth2.serverUrl}")
    private String serverUrl;

    @Value("${scoutventure.oauth2.realm}")
    private String realm;

    @Value("${scoutventure.oauth2.backend.clientId}")
    private String clientId;

    @Value("${scoutventure.oauth2.backend.clientSecret}")
    private String clientSecret;

    @Value("${scoutventure.oauth2.provider}")
    private String provider;

    @Bean
    @Primary
    public IamAdapter iamAdapterImpl(KeycloakAdapter keycloakAdapter) {
        if ("keycloak".equalsIgnoreCase(provider)) {
            return keycloakAdapter;
        }
        throw new IllegalArgumentException("Unsupported provider: " + provider);
    }

}
