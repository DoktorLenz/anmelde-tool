package de.stinner.anmeldetoolbackend.domain.auth.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RegistrationRepository extends JpaRepository<RegistrationEntity, UUID> {
    
}
