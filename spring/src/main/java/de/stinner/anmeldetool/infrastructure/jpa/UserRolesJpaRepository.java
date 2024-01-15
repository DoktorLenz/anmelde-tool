package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.infrastructure.jpa.models.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesJpaRepository extends JpaRepository<UserRolesEntity, String> {
}
