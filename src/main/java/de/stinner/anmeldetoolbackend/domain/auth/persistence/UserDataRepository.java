package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserDataRepository extends JpaRepository<UserDataEntity, UUID> {

    Optional<UserDataEntity> findByEmail(final String email);
}
