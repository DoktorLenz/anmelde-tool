package dev.stinner.scoutventure.domain.ports.spi;

import dev.stinner.scoutventure.domain.models.User;

import java.util.List;

public interface UserRepository {
    void saveAllAndFlush(List<User> users);
}
