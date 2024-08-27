package dev.stinner.scoutventure.infrastructure.iam;

import dev.stinner.scoutventure.domain.models.User;
import dev.stinner.scoutventure.domain.ports.spi.IamAdapter;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class KeycloakAdapter implements IamAdapter {

    private final IamConfiguration iamConfiguration;

    private Keycloak connectToKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(iamConfiguration.getServerUrl())
                .realm(iamConfiguration.getRealm())
                .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                .clientId(iamConfiguration.getClientId())
                .clientSecret(iamConfiguration.getClientSecret())
                .build();
    }

    public List<User> getUsers() {
        try (Keycloak kc = connectToKeycloak()) {
            List<UserRepresentation> users = kc.realm(iamConfiguration.getRealm()).users().list();
            return users.parallelStream().map(user -> new User(
                    user.getId(),
                    user.getFirstName(),
                    user.getLastName(),
                    user.getUsername(),
                    user.getEmail())
            ).toList();
        }
    }

    //    @Scheduled(fixedRate = 120 * 60 * 1000)
    public void test() {
        try (
                Keycloak kc = KeycloakBuilder.builder()
                        .serverUrl("http://localhost:8080")
                        .realm("scoutventure")
                        .grantType(OAuth2Constants.CLIENT_CREDENTIALS)
                        .clientId("72c5071a605e4c4aad900266fcefdc0e@scoutventure-backend")
                        .clientSecret("pIjsFDKaRXRBfGEjs4OTjiLLRjNEfVii")
                        .build()) {
            UsersResource ur = kc.realm("scoutventure").users();
            ur.count();
        }
    }
}
