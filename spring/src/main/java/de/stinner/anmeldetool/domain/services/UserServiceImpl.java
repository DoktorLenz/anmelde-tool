package de.stinner.anmeldetool.domain.services;

import de.stinner.anmeldetool.domain.ports.api.UserService;
import de.stinner.anmeldetool.domain.ports.spi.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRolesRepository userRolesRepository;

    /**
     * Gets roles for a user (subject)
     *
     * @param subject subject of the JWT - can be in many formats
     * @return roles for the given subject
     */
    public List<String> getRolesForSubject(String subject) {
        return userRolesRepository.findBySubject(subject).getRoles();
    }
}
