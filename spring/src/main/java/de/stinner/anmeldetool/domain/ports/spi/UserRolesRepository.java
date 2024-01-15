package de.stinner.anmeldetool.domain.ports.spi;

import de.stinner.anmeldetool.domain.models.UserRoles;

public interface UserRolesRepository {
    UserRoles findBySubject(String subject);
}
