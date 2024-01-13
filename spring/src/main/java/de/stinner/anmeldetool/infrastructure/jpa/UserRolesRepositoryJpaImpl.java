package de.stinner.anmeldetool.infrastructure.jpa;

import de.stinner.anmeldetool.domain.ports.spi.UserRolesRepository;
import de.stinner.anmeldetool.infrastructure.jpa.models.UserRolesEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRolesRepositoryJpaImpl implements UserRolesRepository {

    private final UserRolesJpaRepository userRolesJpaRepository;

    @Transactional
    @Override
    public UserRolesEntity findBySubject(String subject) {
        return userRolesJpaRepository.findById(subject).orElseGet(() -> addSubjectToUserRoles(subject));
    }

    private UserRolesEntity addSubjectToUserRoles(String subject) {
        UserRolesEntity entity = new UserRolesEntity(subject);
        return userRolesJpaRepository.save(entity);
    }
}
