package dev.stinner.scoutventure.application.rest.security;

import lombok.Data;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class OAuth2FrontendConfiguration {

    @Value("${scoutventure.oauth2.serverUrl}")
    private String serverUrl;

    @Value("${scoutventure.oauth2.realm}")
    private String realm;

    @Getter
    @Value("${scoutventure.oauth2.frontend.clientId}")
    private String clientId;

    public String getIssuerUri() {
        return serverUrl + "/realms/" + realm;
    }

}
