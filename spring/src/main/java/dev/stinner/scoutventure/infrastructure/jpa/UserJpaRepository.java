package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.infrastructure.jpa.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
}
