package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.base.BaseControllerTest;
import de.stinner.anmeldetool.hexagonal.application.rest.RestActuatorEndpoints;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

class ActuatorIT extends BaseControllerTest {

    @Test
    void readinessEndpointShouldBeReachableWithoutAuth() {
        given().when().get(RestActuatorEndpoints.READINESS)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void livenessActuatorShouldBeReachableWithoutAuth() {
        given().when().get(RestActuatorEndpoints.LIVENESS)
                .then().statusCode(HttpStatus.OK.value());
    }
}
