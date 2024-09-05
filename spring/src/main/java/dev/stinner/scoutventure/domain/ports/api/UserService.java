package dev.stinner.scoutventure.domain.ports.api;

import dev.stinner.scoutventure.domain.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
}
