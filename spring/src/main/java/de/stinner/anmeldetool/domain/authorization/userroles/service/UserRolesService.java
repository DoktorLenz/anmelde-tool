package de.stinner.anmeldetool.domain.authorization.userroles.service;

import de.stinner.anmeldetool.domain.authorization.userroles.persistence.UserRolesEntity;
import de.stinner.anmeldetool.domain.authorization.userroles.persistence.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserRolesService {

    private final UserRolesRepository userRolesRepository;

    /**
     * Gets roles for a user (subject).
     * If user is not found, it will add the user to the table with no roles.
     *
     * @param subject subject of the JWT - can be in many formats
     * @return roles for the given subject
     */
    @Transactional
    public Set<String> getRolesFromSubject(String subject) {
        List<UserRolesEntity> list = userRolesRepository.findAll();

        return userRolesRepository.findById(subject).orElseGet(() -> addUserToUserRoles(subject)).getRoles();
    }

    private UserRolesEntity addUserToUserRoles(String subject) {
        UserRolesEntity entity = new UserRolesEntity(subject);
        return userRolesRepository.save(entity);
    }
}
