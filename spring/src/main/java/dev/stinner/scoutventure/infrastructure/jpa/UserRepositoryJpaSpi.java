package dev.stinner.scoutventure.infrastructure.jpa;

import dev.stinner.scoutventure.domain.models.User;
import dev.stinner.scoutventure.domain.ports.spi.UserRepository;
import dev.stinner.scoutventure.infrastructure.jpa.models.UserEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Repository
public class UserRepositoryJpaSpi implements UserRepository {

    private final UserJpaRepository userJpaRepository;

    public List<User> getAllUsers() {
        return userJpaRepository.findAll().stream().map(UserEntity::toDomain).toList();
    }

    @Transactional
    @Override
    public void updateUsers(List<User> users) {
        var fetchedUsers = users.stream().map(UserEntity::fromDomain).toList();
        var currentUsers = userJpaRepository.findAll();

        Set<String> currentUserIds = currentUsers.stream().map(UserEntity::getSubject).collect(Collectors.toSet());
        Set<String> fetchedUserIds = fetchedUsers.stream().map(UserEntity::getSubject).collect(Collectors.toSet());

        // Users to delete (present in DB but not in IAM)
        Set<String> usersToDelete = new HashSet<>(currentUserIds);
        usersToDelete.removeAll(fetchedUserIds);

        // Users to add (present in IAM but not in DB)
        Set<String> usersToAdd = new HashSet<>(fetchedUserIds);
        usersToAdd.removeAll(currentUserIds);

        // Users to update (present in both DB and IAM)
        Set<String> usersToUpdate = new HashSet<>(currentUserIds);
        usersToUpdate.removeAll(fetchedUserIds);

        // handle deletions
        if (!usersToDelete.isEmpty()) {
            userJpaRepository.deleteBySubjectIn(usersToDelete);
        }

        // handle additions
        List<UserEntity> newUsers = fetchedUsers.stream()
                .filter(user -> usersToAdd.contains(user.getSubject()))
                .toList();

        userJpaRepository.saveAll(newUsers);

        // handle modifications
        List<UserEntity> updatedUsers = fetchedUsers.stream()
                .filter(user -> usersToUpdate.contains(user.getSubject()))
                .toList();

        userJpaRepository.saveAll(updatedUsers);
    }
}
