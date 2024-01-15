package de.stinner.anmeldetool.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRoles {
    /**
     * The subject in a jwt used for oauth has no specified format.
     * e.g. Zitadel uses a random number, Keycloak uses an uuid, etc.
     */
    private String subject;

    private List<String> roles;

}
