package de.stinner.anmeldetool.hexagonal.infrastructure.jpa;

import de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesJpaRepository extends JpaRepository<UserRolesEntity, String> {
}
