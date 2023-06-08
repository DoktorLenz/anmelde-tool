package de.stinner.anmeldetool.domain.authorization.userroles.persistence;

import de.stinner.anmeldetool.domain.authorization.model.Role;
import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_roles")
public class UserRolesEntity {

    @Id
    @Column(nullable = false, updatable = false)
    private String subject;

    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                    name = AbstractArrayType.SQL_ARRAY_TYPE,
                    value = "roles"
            )
    )
    @Column(
            name = "roles",
            columnDefinition = "roles[]"
    )
    private Role[] roles;

    public List<Role> getRoles() {
        return List.of(roles);
    }
}
