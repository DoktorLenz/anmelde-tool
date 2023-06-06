package de.stinner.anmeldetool.application.auth;

import de.stinner.anmeldetool.application.rest.ActuatorEndpoints;
import de.stinner.anmeldetool.base.BaseControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static io.restassured.RestAssured.given;

class ActuatorIT extends BaseControllerTest {

    @Test
    void readinessEndpointShouldBeReachableWithoutAuth() {
        given().when().get(ActuatorEndpoints.READINESS)
                .then().statusCode(HttpStatus.OK.value());
    }

    @Test
    void livenessActuatorShouldBeReachableWithoutAuth() {
        given().when().get(ActuatorEndpoints.LIVENESS)
                .then().statusCode(HttpStatus.OK.value());
    }
}
