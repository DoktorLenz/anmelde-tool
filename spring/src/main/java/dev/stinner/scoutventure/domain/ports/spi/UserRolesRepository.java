package dev.stinner.scoutventure.domain.ports.spi;

import dev.stinner.scoutventure.domain.models.UserRoles;

public interface UserRolesRepository {
    UserRoles findBySubject(String subject);
}
