package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import de.stinner.anmeldetoolbackend.domain.auth.service.Authority;
import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;

import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "users_")
public class UserDataEntity {
    // Data used for spring authentication
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String email;
    private String password;
    private boolean accountLocked;
    private boolean credentialsExpired;
    private boolean enabled;

    @Type(
            value = EnumArrayType.class,
            parameters = @Parameter(
                    name = AbstractArrayType.SQL_ARRAY_TYPE,
                    value = "authority"
            )
    )
    @Column(
            name = "authorities",
            columnDefinition = "authority[]"
    )
    private Authority[] authorities;

    // Custom user data
    private String firstname;
    private String lastname;
}
