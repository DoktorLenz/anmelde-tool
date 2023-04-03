package de.stinner.anmeldetool.domain.auth.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.WithMockUser;


public class AuthControllerTest extends BaseControllerTest {

    @Test
    public void testLogin() {
        baseRequest.with()
                .auth().basic("bar@localhost", "validpassword")
                .when().get(ApiEndpoints.V1.Auth.LOGIN)
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @WithMockUser
    public void testLogout() {
        baseRequest.when().get(ApiEndpoints.V1.Auth.LOGOUT).then().statusCode(HttpStatus.NO_CONTENT.value());
        baseRequest.when().get(ApiEndpoints.V1.Auth.LOGIN).then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
