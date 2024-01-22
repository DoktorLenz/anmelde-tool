package dev.stinner.scoutventure.infrastructure.jpa.models;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UserRolesEntityTest {

    @Test
    void newUserRoleEntity_shouldNotHaveAnyRoles() {
        UserRolesEntity entity1 = new UserRolesEntity();
        UserRolesEntity entity2 = new UserRolesEntity("subject");

        assertThat(entity1.getRoles()).isNotNull().isEmpty();
        assertThat(entity2.getRoles()).isNotNull().isEmpty();
    }
}
