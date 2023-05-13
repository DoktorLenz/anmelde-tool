package de.stinner.anmeldetool.domain.auth.persistence;

import de.stinner.anmeldetool.application.auth.WebSecurityConfiguration;
import de.stinner.anmeldetool.application.rest.error.ErrorMessages;
import de.stinner.anmeldetool.domain.auth.service.Authority;
import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
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
    @Setter(AccessLevel.NONE)
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

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    // Custom user data
    private String firstname;
    private String lastname;

    /**
     * Creates a new enabled User with no authorities.
     * Password will be encoded by the encoder provided by WebSecurityConfiguration.
     *
     * @param registrationEntity User information from registration
     * @param password           Clear text password
     * @return UserData with encoded password and data from registration
     */
    public static UserDataEntity create(RegistrationEntity registrationEntity, String password) {
        UserDataEntity entity = new UserDataEntity();
        entity.email = registrationEntity.getEmail();
        entity.setPassword(password);
        entity.accountLocked = false;
        entity.credentialsExpired = false;
        entity.enabled = true;
        entity.authorities = new Authority[]{};
        entity.firstname = registrationEntity.getFirstname();
        entity.lastname = registrationEntity.getLastname();
        return entity;
    }

    /**
     * Sets a new password using the encoder provided by WebSecurityConfiguration
     * and removes the expiration flag of the credentials.
     *
     * @param password Clear text password
     */
    public void setPassword(String password) {
        this.password = WebSecurityConfiguration.encoder().encode(password);
        this.credentialsExpired = false;
    }

    public void changePassword(String oldPassword, String newPassword) {
        if (!WebSecurityConfiguration.encoder().matches(oldPassword, this.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ErrorMessages.C400.WRONG_PASSWORD);
        }
        this.setPassword(newPassword);
    }
}
