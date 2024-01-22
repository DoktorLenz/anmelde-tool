package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.domain.models.UserRoles;
import dev.stinner.scoutventure.domain.ports.spi.UserRolesRepository;
import dev.stinner.scoutventure.infrastructure.jpa.models.UserRolesEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRolesRepositoryJpaSpi implements UserRolesRepository {

    private final UserRolesJpaRepository userRolesJpaRepository;

    @Transactional
    @Override
    public UserRoles findBySubject(String subject) {
        return userRolesJpaRepository.findById(subject)
                .orElseGet(() -> addSubjectToUserRoles(subject))
                .toDomain();
    }

    private UserRolesEntity addSubjectToUserRoles(String subject) {
        UserRolesEntity entity = new UserRolesEntity(subject);
        return userRolesJpaRepository.save(entity);
    }
}
