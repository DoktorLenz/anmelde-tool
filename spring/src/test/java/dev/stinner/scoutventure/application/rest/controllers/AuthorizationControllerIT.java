package dev.stinner.scoutventure.application.rest.controllers;

import dev.stinner.scoutventure.application.rest.RestApiEndpoints;
import dev.stinner.scoutventure.application.rest.security.Role;
import dev.stinner.scoutventure.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.assertj.core.api.Assertions.assertThat;

class AuthorizationControllerIT extends BaseControllerTest {
    @Test
    @WithMockUser(roles = {"VERIFIED"})
    void when_getUserRoles_with_verified_user_then_ResponseEntity_with_200_and_verify_role_returned() {
        String[] roles = given().when().get(RestApiEndpoints.V1.Auth.USERROLES)
                .then().status(HttpStatus.OK)
                .extract().as(String[].class);

        assertThat(roles).containsOnly("ROLE_" + Role.VERIFIED);
    }

    @Test
    @WithMockUser(roles = {})
    void when_getUserRoles_with_no_roles_user_then_ResponseEntity_with_200_and_no_roles_returned() {
        String[] roles = given().when().get(RestApiEndpoints.V1.Auth.USERROLES)
                .then().status(HttpStatus.OK)
                .extract().as(String[].class);

        assertThat(roles).isEmpty();
    }
}
