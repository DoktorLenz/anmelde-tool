package dev.stinner.scoutventure.keycloak;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Setter
@Getter
@ConfigurationProperties(prefix = "keycloak.server")
public class KeycloakServerProperties {

    String contextPath = "/api/v1/auth";
    String realmImportFile = "scoutventure-realm.json";
    AdminUser adminUser = new AdminUser();

    @Getter
    @Setter
    public static class AdminUser {
        String username = "admin";
        String password = "admin";

    }
}
