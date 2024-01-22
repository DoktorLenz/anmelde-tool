package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.infrastructure.jpa.models.UserRolesEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesJpaRepository extends JpaRepository<UserRolesEntity, String> {
}
