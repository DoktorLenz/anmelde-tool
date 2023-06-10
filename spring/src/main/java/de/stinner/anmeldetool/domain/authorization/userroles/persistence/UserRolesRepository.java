package de.stinner.anmeldetool.domain.authorization.userroles.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRolesRepository extends JpaRepository<UserRolesEntity, String> {
}
