package de.stinner.anmeldetool.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, UUID> {
    Iterable<RegistrationEntity> findAllByEmailSentIsNull();

    Optional<RegistrationEntity> findByRegistrationIdAndCreatedAtIsAfterAndEmailSentIsTrue(UUID registrationId, Instant after);

    void deleteAllByEmailSentIsBefore(Instant before);
}
