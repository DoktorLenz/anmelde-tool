package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

public interface ResetPasswordRepository extends JpaRepository<ResetPasswordEntity, UUID> {


//    Optional<ResetPasswordEntity> findByResetIdAndCreatedAtIsAfterAndEmailSentIsTrue(@Param("resetId") UUID resetId, @Param("createdAt") Instant after);

    Optional<ResetPasswordEntity> findByResetIdAndEmailSentAfter(UUID resetId, Instant after);

    void deleteAllByUserEquals(UserDataEntity entity);

    Iterable<ResetPasswordEntity> findAllByEmailSentIsNull();

    void deleteAllByCreatedAtIsBefore(Instant before);
}
