package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.infrastructure.jpa.models.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserJpaRepository extends JpaRepository<UserEntity, String> {
    void deleteBySubjectIn(Set<String> usersToDelete);
}
