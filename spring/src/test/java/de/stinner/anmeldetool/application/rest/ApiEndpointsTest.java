package de.stinner.anmeldetool.application.rest;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class ApiEndpointsTest {
    @Test
    void allPathsMustContainApi() {
        assertThatFieldsContainingString(ApiEndpoints.class, "/api");
    }

    @Test
    void allPathsInV1MustContainV1() {
        assertThatFieldsContainingString(ApiEndpoints.V1.class, "/v1");
    }

    @Test
    void allPathsInAuthMustContainAuth() {
        assertThatFieldsContainingString(ApiEndpoints.V1.Auth.class, "/auth");
    }

    private void assertThatFieldsContainingString(Class<?> c, String contains) {
        List<Field> fields = Arrays.stream(c.getDeclaredFields())
                .filter(field -> field.getType().equals(String.class))
                .collect(Collectors.toList());

        fields.forEach(AccessibleObject::trySetAccessible);

        assertThat(fields)
                .isNotEmpty()
                .allSatisfy(field -> {
                    assertThat(field.get(null).toString()).contains(contains);
                });


        Class<?>[] innerClasses = c.getClasses();
        Arrays.stream(innerClasses).forEach(innerClass -> assertThatFieldsContainingString(innerClass, contains));
    }
}
