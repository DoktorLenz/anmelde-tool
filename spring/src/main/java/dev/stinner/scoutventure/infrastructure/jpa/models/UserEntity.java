package dev.stinner.scoutventure.infrastructure.jpa.models;

import dev.stinner.scoutventure.domain.models.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "iam_users")
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity {

    /**
     * The subject in a jwt used for oauth has no specified format.
     * e.g. Zitadel uses a random number, Keycloak uses an uuid, etc.
     */
    @Id
    @Column(nullable = false, updatable = false)
    private String subject;

    private String firstname;

    private String lastname;

    private String username;

    private String email;

    public static UserEntity fromDomain(User user) {
        return new UserEntity(
                user.getSubject(),
                user.getFirstname(),
                user.getLastname(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
