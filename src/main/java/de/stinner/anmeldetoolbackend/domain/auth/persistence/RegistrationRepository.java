package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, UUID> {
    Iterable<RegistrationEntity> findByEmailSentIsNull();

    Iterable<RegistrationEntity> deleteByCreatedAtBeforeAndEmailSentIsNotNull(Instant createdAtBefore);
}
