package de.stinner.anmeldetool.domain.auth.api;

import de.stinner.anmeldetool.application.rest.ApiEndpoints;
import de.stinner.anmeldetool.base.BaseControllerTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.module.mockmvc.RestAssuredMockMvc.given;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;


class AuthControllerIT extends BaseControllerTest {

    @Test
    void testLogin() {
        given().auth().with(httpBasic("bar@localhost", "validpassword"))
                .when().get(ApiEndpoints.V1.Auth.LOGIN)
                .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    void testLogout() {
        given().contentType(ContentType.JSON)
                .auth()
                .with(httpBasic("bar@localhost", "validpassword"))
                .when().get(ApiEndpoints.V1.Auth.LOGIN)
                .then().statusCode(HttpStatus.NO_CONTENT.value());


        given().contentType(ContentType.JSON)
                .when().get(ApiEndpoints.V1.Auth.LOGOUT)
                .then().statusCode(HttpStatus.NO_CONTENT.value());

        given().contentType(ContentType.JSON)
                .when().get(ApiEndpoints.V1.Auth.LOGIN)
                .then().statusCode(HttpStatus.UNAUTHORIZED.value());
    }
}
