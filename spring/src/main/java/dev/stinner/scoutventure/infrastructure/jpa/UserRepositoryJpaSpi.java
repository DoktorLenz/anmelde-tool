package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.domain.models.User;
import dev.stinner.scoutventure.domain.ports.spi.UserRepository;
import dev.stinner.scoutventure.infrastructure.jpa.models.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepositoryJpaSpi implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    @Transactional
    @Override
    public void saveAllAndFlush(List<User> users) {
        userJpaRepository.saveAllAndFlush(users.stream().map(UserEntity::fromDomain).toList());
    }
}
