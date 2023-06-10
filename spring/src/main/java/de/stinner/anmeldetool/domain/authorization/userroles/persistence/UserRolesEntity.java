package de.stinner.anmeldetool.domain.authorization.userroles.persistence;

import io.hypersistence.utils.hibernate.type.array.ListArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "user_roles")
public class UserRolesEntity {

    /**
     * The subject in a jwt used for oauth has no specified format.
     * e.g. Zitadel uses a random number, Keycloak uses an uuid, etc.
     */
    @Id
    @Column(nullable = false, updatable = false)
    private String subject;

    @Type(ListArrayType.class)
    private List<String> roles;

    public UserRolesEntity(String subject) {
        this.subject = subject;
        this.roles = new ArrayList<>();
    }
}
