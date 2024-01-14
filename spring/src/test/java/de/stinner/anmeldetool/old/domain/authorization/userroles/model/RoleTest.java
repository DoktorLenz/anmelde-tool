package de.stinner.anmeldetool.old.domain.authorization.userroles.model;

import de.stinner.anmeldetool.application.rest.security.Role;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static java.lang.reflect.Modifier.isStatic;
import static org.assertj.core.api.Assertions.assertThat;

class RoleTest {

    @Test
    @SneakyThrows
    // sonar false positive for assertThat order
    @SuppressWarnings("java:S3415")
    void superuser_shouldContainAllRoles() {
        List<String> allRoles = Arrays.stream(Role.class.getFields())
                .filter(field -> field.getType().equals(String.class) && isStatic(field.getModifiers()))
                .map(field -> {
                    field.setAccessible(true);
                    try {
                        return (String) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                })
                .toList();

        assertThat(Role.SUPERUSER).containsAll(allRoles);
    }
}
