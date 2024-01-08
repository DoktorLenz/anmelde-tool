package de.stinner.anmeldetool.hexagonal.domain.ports.spi;

import de.stinner.anmeldetool.hexagonal.infrastructure.jpa.models.UserRolesEntity;

public interface UserRolesRepository {
    UserRolesEntity findBySubject(String subject);
}
