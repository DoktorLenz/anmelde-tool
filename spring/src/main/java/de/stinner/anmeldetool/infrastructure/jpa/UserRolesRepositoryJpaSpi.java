package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.domain.models.UserRoles;
import de.stinner.anmeldetool.domain.ports.spi.UserRolesRepository;
import de.stinner.anmeldetool.infrastructure.jpa.models.UserRolesEntity;
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
