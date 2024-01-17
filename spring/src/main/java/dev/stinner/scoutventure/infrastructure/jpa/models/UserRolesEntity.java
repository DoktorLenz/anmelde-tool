package dev.stinner.scoutventure.infrastructure.jpa.models;

import dev.stinner.scoutventure.domain.models.UserRoles;
import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_roles")
@AllArgsConstructor
@NoArgsConstructor
public class UserRolesEntity {

    /**
     * The subject in a jwt used for oauth has no specified format.
     * e.g. Zitadel uses a random number, Keycloak uses an uuid, etc.
     */
    @Id
    @Column(nullable = false, updatable = false)
    private String subject;

    @Type(ListArrayType.class)
    private List<String> roles = new ArrayList<>();

    public UserRolesEntity(String subject) {
        this.subject = subject;
    }

    public static UserRolesEntity fromDomain(UserRoles domain) {
        return new UserRolesEntity(
                domain.getSubject(),
                domain.getRoles()
        );
    }

    public UserRoles toDomain() {
        return new UserRoles(
                subject,
                roles
        );
    }

    public List<String> getRoles() {
        return this.roles.isEmpty() ? new ArrayList<>() : this.roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles.isEmpty() ? new ArrayList<>() : roles;
    }
}
