package de.stinner.anmeldetool.domain.ports.spi;

import de.stinner.anmeldetool.infrastructure.jpa.models.UserRolesEntity;

public interface UserRolesRepository {
    UserRolesEntity findBySubject(String subject);
}
