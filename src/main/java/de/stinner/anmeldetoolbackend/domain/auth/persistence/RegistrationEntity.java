package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import de.stinner.anmeldetoolbackend.domain.auth.api.model.RegistrationRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import java.time.Instant;
import java.util.UUID;


@Getter
@Setter
@Entity
@Table(name = "registrations")
public class RegistrationEntity {
    @Id
    @Column(updatable = false, nullable = false)
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID registrationId;

    @Column(updatable = false, nullable = false)
    private String firstname;

    @Column(updatable = false, nullable = false)
    private String lastname;

    @Column(updatable = false, nullable = false)
    private String email;

    @CreationTimestamp
    @Column(updatable = false, nullable = false)
    private Instant createdAt;

    public static RegistrationEntity of(RegistrationRequestDto dto) {
        RegistrationEntity entity = new RegistrationEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
