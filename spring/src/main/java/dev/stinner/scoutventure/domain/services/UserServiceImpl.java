package dev.stinner.scoutventure.domain.services;

import dev.stinner.scoutventure.domain.models.User;
import dev.stinner.scoutventure.domain.ports.api.UserService;
import dev.stinner.scoutventure.domain.ports.spi.IamAdapter;
import dev.stinner.scoutventure.domain.ports.spi.UserRepository;
import dev.stinner.scoutventure.domain.ports.spi.UserRolesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRolesRepository userRolesRepository;
    private final IamAdapter iamAdapter;
    private final UserRepository userRepository;

    /**
     * Gets roles for a user (subject)
     *
     * @param subject subject of the JWT - can be in many formats
     * @return roles for the given subject
     */
    public List<String> getRolesForSubject(String subject) {
        return userRolesRepository.findBySubject(subject).getRoles();
    }

    /**
     * Synchronises users of the IaM with the internal users
     */
    @Scheduled(fixedRate = 120 * 60 * 1000)
    public void syncIamUsers() {
        List<User> users = iamAdapter.getUsers();
        userRepository.saveAllAndFlush(users);
    }
}
