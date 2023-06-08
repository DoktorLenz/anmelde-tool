package de.stinner.anmeldetool.domain.authorization.userroles.persistence;

import org.springframework.data.repository.CrudRepository;

public interface UserRolesRepository extends CrudRepository<UserRolesEntity, String> {
}
