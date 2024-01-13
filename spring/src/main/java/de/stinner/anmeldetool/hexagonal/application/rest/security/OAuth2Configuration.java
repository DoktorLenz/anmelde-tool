package de.stinner.anmeldetool.hexagonal.application.rest.security;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties(prefix = "anmelde-tool.oauth2")
public class OAuth2Configuration {
    private String issuerUri;
    private String clientId;
    private List<String> secureRoutes;
}
